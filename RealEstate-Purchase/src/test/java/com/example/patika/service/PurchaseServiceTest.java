package com.example.patika.service;


import com.example.patika.client.PaymentClient;
import com.example.patika.client.UserClient;
import com.example.patika.client.request.UserBalanceRequest;
import com.example.patika.client.response.IndividualUserResponse;
import com.example.patika.dto.request.PurchaseRequest;
import com.example.patika.dto.request.PurchaseStatusRequest;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.dto.response.PurchaseResponse;
import com.example.patika.model.Purchase;
import com.example.patika.model.enums.PurchaseStatus;
import com.example.patika.queue.QueueService;
import com.example.patika.repository.PurchaseDao;
import com.example.patika.service.impl.PurchaseServiceImpl;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import com.example.patika.utils.results.SuccessResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
public class PurchaseServiceTest {
    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Mock
    private PurchaseDao purchaseDao;

    @Mock
    private DtoConverterService dtoConverterService;

    @Mock
    private UserClient userClient;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private QueueService rabbitMqService;


    @BeforeEach
    void setup() {

        Mockito.when(dtoConverterService.entityToDto(prepareMockPurchaseList(), PurchaseResponse.class))
                .thenReturn(prepareMockPurchaseResponseList());

        Mockito.when(dtoConverterService.dtoToEntity(preparePurchaseRequest(1), Purchase.class))
                .thenReturn(preparePurchase(1));

        Mockito.when(dtoConverterService.entityToDtoSingle(preparePurchase(1), PurchaseResponse.class))
                .thenReturn(preparePurchaseResponse(1));


        Mockito
                .when(purchaseDao.findAll())
                .thenReturn(prepareMockPurchaseList());


        Mockito
                .when(purchaseDao.save(any()))
                .thenReturn(preparePurchase(1));


        Mockito
                .when(userClient.findById(1))
                .thenReturn(prepareIndividualUserResponse());
        Mockito
                .when(paymentClient.pay(any()))
                .thenReturn(new SuccessResult());

    }


    @Test
    void findAllTest() {

        Mockito.when(dtoConverterService.entityToDto(prepareMockPurchaseList(), PurchaseResponse.class))
                .thenReturn(prepareMockPurchaseResponseList());

        DataResult<List<PurchaseResponse>> allAdvert = purchaseService.findAll();

        assertNotNull(allAdvert);


    }


    @Test
    void purchaseTest() {


        assertDoesNotThrow(() -> {
            Result response = purchaseService.purchase(preparePurchaseRequest(1), 1);
            assertNotNull(response);

            assertEquals(true, response.isSuccess());

        });
    }


    @Test
    void updatePurchaseStatusByIdTest() {

        PurchaseStatusRequest request = new PurchaseStatusRequest();
        Purchase purchase = preparePurchase(1);

        Mockito
                .when(purchaseDao.findById(1))
                .thenReturn(purchase);


        assertDoesNotThrow(() -> {
            Result response = purchaseService.updatePurchaseStatusById(1, request);

            assertEquals(true, response.isSuccess());

        });

    }


    @Test
    void findByIdTest() {
        Purchase purchase = preparePurchase(1);

        Mockito
                .when(purchaseDao.findById(1))
                .thenReturn(purchase);


        DataResult<PurchaseResponse> response = purchaseService.findById(1);
        assertNotNull(response);
        assertEquals(PurchaseStatus.PURCHASED, response.getData().getPurchaseStatus());

    }

    @Test
    void findAllByUserId() {
        Purchase purchase = preparePurchase(1);

        Mockito
                .when(purchaseDao.findAllByUserId(1))
                .thenReturn(prepareMockPurchaseList());


        DataResult<List<PurchaseResponse>> allAdvert = purchaseService.findAllByUserId(1);

        assertNotNull(allAdvert);

        assertThat(allAdvert.getData().size()).isNotZero();

    }

    private UserBalanceIdentificationRequest prepareUserBalanceIdentificationRequest() {

        return new UserBalanceIdentificationRequest(1, 1, 10, 30);
    }

    private UserBalanceRequest prepareUserBalanceRequest() {
        LocalDate localDate = LocalDate.now();
        return new UserBalanceRequest(1, 10, localDate);
    }

    private DataResult<IndividualUserResponse> prepareIndividualUserResponse() {
        LocalDate localDate = LocalDate.of(2022, 2, 15);
        DataResult<IndividualUserResponse> response = new DataResult<IndividualUserResponse>(new IndividualUserResponse(1, "mail", "123456", "url", "555555", "yunus", "koç", localDate), true);
        return response;
    }


    private List<PurchaseResponse> prepareMockPurchaseResponseList() {
        List<PurchaseResponse> purchaseResponseList = new ArrayList<PurchaseResponse>();
        purchaseResponseList.add(preparePurchaseResponse(1));
        purchaseResponseList.add(preparePurchaseResponse(2));
        purchaseResponseList.add(preparePurchaseResponse(3));
        purchaseResponseList.add(preparePurchaseResponse(4));
        return purchaseResponseList;
    }

    private List<Purchase> prepareMockPurchaseList() {
        List<Purchase> purchaseList = new ArrayList<Purchase>();
        purchaseList.add(preparePurchase(1));
        purchaseList.add(preparePurchase(2));
        purchaseList.add(preparePurchase(3));
        purchaseList.add(preparePurchase(4));
        return purchaseList;
    }

    private Purchase preparePurchase(int id) {
        return new Purchase(id, 1, 1, PurchaseStatus.PURCHASED);

    }

    private PurchaseRequest preparePurchaseRequest(int id) {
        return new PurchaseRequest(id, 1, 1, PurchaseStatus.PURCHASED);

    }

    private PurchaseStatusRequest preparePurchaseStatusRequest(int id) {
        return new PurchaseStatusRequest(id, PurchaseStatus.PURCHASED);

    }

    private PurchaseResponse preparePurchaseResponse(int id) {
        return new PurchaseResponse(id, 1, 1, PurchaseStatus.PURCHASED);

    }

}
