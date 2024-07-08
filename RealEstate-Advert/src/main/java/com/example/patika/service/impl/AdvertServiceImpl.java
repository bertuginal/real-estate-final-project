package com.example.patika.service.impl;

import com.example.patika.client.UserClient;
import com.example.patika.client.request.UserBalanceRequest;
import com.example.patika.client.response.UserBalanceResponse;
import com.example.patika.dto.request.AdvertRequest;
import com.example.patika.dto.request.AdvertStatusRequest;
import com.example.patika.dto.request.AdvertUpdateRequest;
import com.example.patika.dto.response.AdvertResponse;
import com.example.patika.exception.AdvertNotFoundException;
import com.example.patika.model.Advert;
import com.example.patika.model.enums.AdvertStatus;
import com.example.patika.queue.QueueService;
import com.example.patika.repository.AdvertDao;
import com.example.patika.service.AdvertService;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdvertServiceImpl implements AdvertService {

    private AdvertDao advertDao;
    private DtoConverterService dtoConverterService;
    private UserClient userClient;
    private QueueService rabbitMqService;

    @Autowired
    public AdvertServiceImpl(AdvertDao advertDao, DtoConverterService dtoConverterService, UserClient userClient, QueueService rabbitMqService) {
        this.advertDao = advertDao;
        this.dtoConverterService = dtoConverterService;
        this.userClient = userClient;
        this.rabbitMqService = rabbitMqService;
    }


    @Override
    public DataResult<List<AdvertResponse>> findAll() {
        return new SuccessDataResult<List<AdvertResponse>>(dtoConverterService.entityToDto(advertDao.findAll(), AdvertResponse.class),
                "all adverts listed");
    }

    //login yapan kullanıcının idsine göre ilan yayınlar
    //kullanıcının kalan haklarını kontrol eder. Paketin süresi geçmişse kalan haklarını sıfırlar error result döner.
    //süresi geçmemişse kalan hakları 0 ve 0 dan küçükse error result döner.
    //süresi geçmemişse ve kalan hak varsa. İlan IN_REVIEW olarak oluşturulur.
    @Override
    public Result add(AdvertRequest advertRequest,int id) {
        advertRequest.setUserId(id);
        DataResult<UserBalanceResponse> response = userClient.findByIdForBalance(advertRequest.getUserId()); // Feign Exception  GlobalExceptionHandler tarafından handle ediliyor.  User bulunmazsa exception atıyor.
        UserBalanceRequest userBalanceRequest = new UserBalanceRequest();
        if (response.getData().getEndDateOfPackage().isBefore(LocalDate.now()) || response.getData().getAdvertBalance() <= 0) {

            userBalanceRequest.setAdvertBalance(0); // paketin kullanım süresi bittiği için balance sıfırlanır
            userBalanceRequest.setEndDateOfPackage(response.getData().getEndDateOfPackage());
            userClient.updateBalanceById(advertRequest.getUserId(), userBalanceRequest); // Feign Exception  GlobalExceptionHandler tarafından handle ediliyor.
            return new ErrorResult("The advert couldn't be added because the package is undefined or expired or your balance is empty!");
        }
        userBalanceRequest.setAdvertBalance(response.getData().getAdvertBalance() - 1);
        userBalanceRequest.setEndDateOfPackage(response.getData().getEndDateOfPackage());
        userClient.updateBalanceById(advertRequest.getUserId(), userBalanceRequest);

        advertRequest.setCreatedDate(LocalDate.now());
        advertRequest.setEndDate(LocalDate.now().plusDays(30));
        advertRequest.setAdvertStatus(AdvertStatus.IN_REVIEW);
        advertDao.save((Advert) dtoConverterService.dtoToEntity(advertRequest, Advert.class));

        return new SuccessResult("Advert has been successfully added!");
    }

    //id ye göre ilan güncelleme
    @Override
    public Result updateById(int advertId, AdvertUpdateRequest advertUpdateRequest) {

        if (advertDao.findById(advertId) == null) {
            throw new AdvertNotFoundException("Advert not found!");
        }

        Advert advert = advertDao.findById(advertId);
        //Advert advert = (Advert) dtoConverterService.dtoToEntity(advertUpdateRequest, Advert.class);
        prepareAdvert(advert,advertUpdateRequest);

        advertDao.save(advert);

        return new SuccessResult("Advert has been successfully updated!");
    }

    // id ye göre ilan silme
    @Override
    public Result deleteById(int advertId) {
        if (advertDao.findById(advertId) == null) {
            throw new AdvertNotFoundException("Advert not found!");
        }

        advertDao.deleteById(advertId);
        return new SuccessResult("Advert has been successfully deleted!");
    }

    // id ye göre ilan bulma
    @Override
    public DataResult<AdvertResponse> findById(int advertId) {
        if (advertDao.findById(advertId) == null) {
            throw new AdvertNotFoundException("Advert not found!");
        }
        return new SuccessDataResult<AdvertResponse>((AdvertResponse) dtoConverterService.entityToDtoSingle(advertDao.findById(advertId), AdvertResponse.class),
                "Advert has been found!");
    }

    @Override
    public DataResult<List<AdvertResponse>> findAllByUserIdAndIsActive(int userId) {
        // Feign Exception  GlobalExceptionHandler tarafından handle ediliyor. User bulunmazsa exception atıyor.
        userClient.findById(userId);
        return new SuccessDataResult<List<AdvertResponse>>(dtoConverterService.entityToDto(advertDao.findAllByUserIdAndAdvertStatus(userId, AdvertStatus.ACTIVE), AdvertResponse.class),
                "Active adverts have been listed by user id!");
    }

    //kullanıcı id sine göre aktif ilanları getirme
    @Override
    public DataResult<List<AdvertResponse>> findAllByUserIdAndIsPassive(int userId) {
        // Feign Exception  GlobalExceptionHandler tarafından handle ediliyor. User bulunmazsa exception atıyor.
        userClient.findById(userId);
        return new SuccessDataResult<List<AdvertResponse>>(dtoConverterService.entityToDto(advertDao.findAllByUserIdAndAdvertStatus(userId, AdvertStatus.PASSIVE), AdvertResponse.class),
                "Passive adverts have been listed by user id!");
    }

    //rabbitmq kullanarak(asenkron) idye göre ilan statüsünü aktif duruma getirme
    @Override
    public Result updateStatusActiveById(int advertId) {
        if (advertDao.findById(advertId) == null) {
            throw new AdvertNotFoundException("Advert not found!");
        }
        AdvertStatusRequest advertStatusRequest = new AdvertStatusRequest();
        advertStatusRequest.setId(advertId);
        advertStatusRequest.setAdvertStatus(AdvertStatus.ACTIVE);
        rabbitMqService.sendMessage(advertStatusRequest); //ilan durumunu değiştirmek için rabbitmq ile asenkron iletişim
        return new SuccessResult("Status has been updated to active by id!");
    }

    //rabbitmq kullanarak(asenkron) idye göre ilan statüsünü pasif duruma getirme
    @Override
    public Result updateStatusPassiveById(int advertId) {

        if (advertDao.findById(advertId) == null) {
            throw new AdvertNotFoundException("Advert not found!");
        }
        AdvertStatusRequest advertStatusRequest = new AdvertStatusRequest();
        advertStatusRequest.setId(advertId);
        advertStatusRequest.setAdvertStatus(AdvertStatus.PASSIVE);
        rabbitMqService.sendMessage(advertStatusRequest); //ilan durumunu değiştirmek için rabbitmq ile asenkron iletişim
        return new SuccessResult("Status has been updated to passive by id!");
    }

    private Advert prepareAdvert(Advert advert, AdvertUpdateRequest advertUpdateRequest) {

        advert.setCity(advertUpdateRequest.getCity());
        advert.setDistrict(advertUpdateRequest.getDistrict());
        advert.setAdvertType(advertUpdateRequest.getAdvertType());
        advert.setHomeType(advertUpdateRequest.getHomeType());
        advert.setTitle(advertUpdateRequest.getTitle());
        advert.setDescription(advertUpdateRequest.getDescription());
        advert.setNumberOfRooms(advertUpdateRequest.getNumberOfRooms());
        advert.setNumberOfHalls(advertUpdateRequest.getNumberOfHalls());
        advert.setNumberOfBath(advertUpdateRequest.getNumberOfBath());
        advert.setGrossSquareMeters(advertUpdateRequest.getGrossSquareMeters());
        advert.setNetSquareMeters(advertUpdateRequest.getNetSquareMeters());
        advert.setFloorNumber(advertUpdateRequest.getFloorNumber());
        advert.setPrice(advertUpdateRequest.getPrice());
        return advert;
    }

}