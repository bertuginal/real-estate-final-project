package com.example.patika.service.impl;


import com.example.patika.client.PaymentClient;
import com.example.patika.client.UserClient;
import com.example.patika.client.request.PaymentRequest;
import com.example.patika.client.request.UserBalanceRequest;
import com.example.patika.client.response.IndividualUserResponse;
import com.example.patika.dto.request.PurchaseRequest;
import com.example.patika.dto.request.PurchaseStatusRequest;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.dto.response.PurchaseResponse;
import com.example.patika.exception.PaymentFailedException;
import com.example.patika.exception.PurchaseNotFoundException;
import com.example.patika.model.Purchase;
import com.example.patika.model.enums.PurchaseStatus;
import com.example.patika.queue.QueueService;
import com.example.patika.repository.PurchaseDao;
import com.example.patika.service.PurchaseService;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import com.example.patika.utils.results.SuccessDataResult;
import com.example.patika.utils.results.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.patika.packages.AdvertPackage;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseDao purchaseDao;
    private DtoConverterService dtoConverterService;
    private PaymentClient paymentClient;
    private QueueService rabbitMqService;
    private UserClient userClient;

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao, DtoConverterService dtoConverterService, PaymentClient paymentClient,
                               QueueService rabbitMqService, UserClient userClient) {
        this.purchaseDao = purchaseDao;
        this.dtoConverterService = dtoConverterService;
        this.paymentClient = paymentClient;
        this.rabbitMqService = rabbitMqService;
        this.userClient = userClient;
    }


    // Tüm satın almaların listelenmesi işlemi
    @Override
    public DataResult<List<PurchaseResponse>> findAll() {
        return new SuccessDataResult<List<PurchaseResponse>>(dtoConverterService.entityToDto(purchaseDao.findAll(), PurchaseResponse.class),
                "All purchases have been listed!");
    }


    /*
    -> Sisteme login olan kullanıcıya göre satın alma yapılır.
    -> Kullanıcının kalan haklarını kontrol eder.
            * Eğer paketin süresi geçmişse;
                    ** Kalan haklar sıfırlanır.
                    ** Ödeme beklenir. Eğer ödeme yapıldıysa, RabbitMQ tarafından user'a paket tanımlanır.
            * Eğer paketin süresi geçmemişse;
                    ** Kalan haklar sıfırlanmaz.
                    ** Ödeme beklenir. Eğer ödeme yapıldıysa, RabbitMQ tarafından user'a paket tanımlanır.
     */
    @Override
    public Result purchase(PurchaseRequest purchaseRequest, int userId) {
        purchaseRequest.setUserId(userId);
        DataResult<IndividualUserResponse> response = userClient.findById(purchaseRequest.getUserId()); // Feign Exception  GlobalExceptionHandler tarafından handle ediliyor. User bulunmazsa exception atıyor.

        UserBalanceRequest userBalanceRequest = new UserBalanceRequest();
        if (response.getData().getEndDateOfPackage().isBefore(LocalDate.now())) {
            userBalanceRequest.setAdvertBalance(0); // userdaki balance sıfırlanır
            userBalanceRequest.setEndDateOfPackage(LocalDate.now()); // paketin süresi geçmişse; paket bitiş tarihi, paket satın alımı için paketin alındığı tarihe çekilir.
            userClient.updateBalanceById(purchaseRequest.getUserId(), userBalanceRequest);//userclient hata verirse alt satıra geçemez feign exception atar
            response.getData().setAdvertBalance(0); // response nesnesindeki balance sıfırlanır
        }
        AdvertPackage advertPackage = new AdvertPackage();

        if (paymentClient.pay(preparePaymentRequest(advertPackage)).isSuccess() == false) { // ödeme başarısız olursa exception atar
            throw new PaymentFailedException("Payment failed!");
        }

        rabbitMqService.sendMessage(prepareUserBalanceIdentificationRequest(advertPackage, response, purchaseRequest));
        purchaseRequest.setPackageId(advertPackage.getId());
        purchaseRequest.setPurchaseStatus(PurchaseStatus.PURCHASED);
        purchaseDao.save((Purchase) dtoConverterService.dtoToEntity(purchaseRequest, Purchase.class));
        return new SuccessResult("Purchase successful!");
    }


    // Paket iadesi durumunda yapılan update işlemi
    @Override
    public Result updatePurchaseStatusById(int purchaseId, PurchaseStatusRequest purchaseStatusRequest) {

        if (purchaseDao.findById(purchaseId) == null) {
            throw new PurchaseNotFoundException("Purchase not found!");
        }

        Purchase purchase =  purchaseDao.findById(purchaseId);
        purchase.setPurchaseStatus(purchaseStatusRequest.getPurchaseStatus());
        purchaseDao.save(purchase);

        return new SuccessResult("Purchase has been successfully updated!");
    }


    // id'ye göre satın alınan paketin listelenmesi işlemi
    @Override
    public DataResult<PurchaseResponse> findById(int purchaseId) {
        if (purchaseDao.findById(purchaseId) == null) {
            throw new PurchaseNotFoundException("Purchase not found!");
        }

        return new SuccessDataResult<PurchaseResponse>((PurchaseResponse) dtoConverterService.entityToDtoSingle(purchaseDao.findById(purchaseId), PurchaseResponse.class),
                "Purchase found!");
    }

    // Kullanıcı id'sine göre satın alınan paketlerin listelenmesi işlemi.
    @Override
    public DataResult<List<PurchaseResponse>> findAllByUserId(int userId) {
        // Feign Exception, GlobalExceptionHandler tarafından handle ediliyor.
        userClient.findById(userId);
        return new SuccessDataResult<List<PurchaseResponse>>(dtoConverterService.entityToDto(purchaseDao.findAllByUserId(userId), PurchaseResponse.class),
                "Active purchases have been listed by user id!");
    }

    private PaymentRequest preparePaymentRequest( AdvertPackage advertPackage) {
        PaymentRequest paymentRequest =  new PaymentRequest();
        paymentRequest.setAmount(advertPackage.getPrice());
        return paymentRequest;
    }

    private UserBalanceIdentificationRequest prepareUserBalanceIdentificationRequest(AdvertPackage advertPackage, DataResult<IndividualUserResponse> response, PurchaseRequest purchaseRequest) {
        UserBalanceIdentificationRequest userBalanceIdentificationRequest = new UserBalanceIdentificationRequest();
        userBalanceIdentificationRequest.setAdvertBalance(response.getData().getAdvertBalance() + advertPackage.getNumberOfAdvertRights()); // Kullanıcının ilan hakkı ile pakketteki hak toplanır.
        userBalanceIdentificationRequest.setAdditionalDayEndDateOfPackage(advertPackage.getPackageValidityPeriod());// Kullanıcının paket süresine eklenecek gün sayısı.
        userBalanceIdentificationRequest.setUserId(purchaseRequest.getUserId()); // Kullanıcının id'si tanımlandı.
        return userBalanceIdentificationRequest;
    }
}
