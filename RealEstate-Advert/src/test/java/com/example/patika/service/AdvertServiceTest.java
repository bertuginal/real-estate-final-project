package com.example.patika.service;

import com.example.patika.client.UserClient;
import com.example.patika.client.request.UserBalanceRequest;
import com.example.patika.client.response.UserBalanceResponse;
import com.example.patika.dto.request.AdvertRequest;
import com.example.patika.dto.request.AdvertUpdateRequest;
import com.example.patika.dto.response.AdvertResponse;
import com.example.patika.model.Advert;
import com.example.patika.model.enums.AdvertStatus;
import com.example.patika.queue.QueueService;
import com.example.patika.repository.AdvertDao;
import com.example.patika.service.impl.AdvertServiceImpl;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
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
public class AdvertServiceTest {

    @InjectMocks
    private AdvertServiceImpl advertService;

    @Mock
    private AdvertDao advertDao;

    @Mock
    private DtoConverterService dtoConverterService;

    @Mock
    private UserClient userClient;

    @Mock
    private QueueService rabbitMqService;

    @BeforeEach
    void setup() {

        Mockito.when(dtoConverterService.entityToDto(prepareMockAdvertList(), AdvertResponse.class))
                .thenReturn(prepareMockAdvertResponseList());

        Mockito.when(dtoConverterService.dtoToEntity(prepareAdvertRequest(1), Advert.class))
                .thenReturn(prepareAdvert(1));

        Mockito.when(dtoConverterService.entityToDtoSingle(prepareAdvert(1), AdvertResponse.class))
                .thenReturn(prepareAdvertResponse(1));


        Mockito
                .when(advertDao.findAll())
                .thenReturn(prepareMockAdvertList());


        Mockito
                .when(advertDao.save(any()))
                .thenReturn(prepareAdvert(1));

        Mockito
                .when(userClient.findByIdForBalance(1))
                .thenReturn(prepareUserBalanceResponse());

        Mockito
                .when(userClient.updateBalanceById(1, prepareUserBalanceRequest()))
                .thenReturn(prepareUserBalanceResponse());


    }


    @Test
    void findAllTest() {

        DataResult<List<AdvertResponse>> allAdvert = advertService.findAll();

        assertNotNull(allAdvert);

        assertThat(allAdvert.getData().size()).isNotZero();
    }


    @Test
    void addTest() {

        Result response = advertService.add(prepareAdvertRequest(1), 1);
        assertNotNull(response);

        assertEquals(true, response.isSuccess());

    }

    @Test
    void addResponseFalseTest() {

        Mockito
                .when(userClient.findByIdForBalance(1))
                .thenReturn(prepareUserBalanceResponseExpiredDate());

        Result response = advertService.add(prepareAdvertRequest(1), 1);
        assertNotNull(response);

        assertEquals(false, response.isSuccess());

    }

    @Test
    void updateAdvert() {

        AdvertUpdateRequest request = prepareAdvertUpdateRequest(1);
        Advert individualUser = prepareAdvert(1);

        Mockito
                .when(advertDao.findById(1))
                .thenReturn(individualUser);


        assertDoesNotThrow(() -> {
            Result response = advertService.updateById(1, request);

            assertEquals(true, response.isSuccess());

        });

    }

    @Test
    void deleteAdvertById() {

        Advert advert = prepareAdvert(1);

        Mockito
                .when(advertDao.findById(1))
                .thenReturn(advert);

        assertDoesNotThrow(() -> {
            Result response = advertService.deleteById(1);
            Mockito.verify(advertDao).deleteById(any());
            assertEquals(true, response.isSuccess());

        });

    }

    @Test
    void findByIdTest() {
        Advert advert = prepareAdvert(1);

        Mockito
                .when(advertDao.findById(1))
                .thenReturn(advert);


        DataResult<AdvertResponse> response = advertService.findById(1);
        assertNotNull(response);
        assertEquals("açıklama", response.getData().getDescription());

    }

    @Test
    void findAllByUserIdAndIsActive() {
        Advert advert = prepareAdvert(1);

        Mockito
                .when(advertDao.findAllByUserIdAndAdvertStatus(1, AdvertStatus.ACTIVE))
                .thenReturn(prepareMockAdvertList());


        DataResult<List<AdvertResponse>> allAdvert = advertService.findAllByUserIdAndIsActive(1);

        assertNotNull(allAdvert);

        assertThat(allAdvert.getData().size()).isNotZero();

    }

    @Test
    void findAllByUserIdAndIsPassive() {


        Mockito
                .when(advertDao.findAllByUserIdAndAdvertStatus(1, AdvertStatus.PASSIVE))
                .thenReturn(prepareMockAdvertList());


        DataResult<List<AdvertResponse>> allAdvert = advertService.findAllByUserIdAndIsPassive(1);

        assertNotNull(allAdvert);

        assertThat(allAdvert.getData().size()).isNotZero();

    }


    @Test
    void updateStatusActiveByIdTest() {
        Advert advert = prepareAdvert(1);

        Mockito
                .when(advertDao.findById(1))
                .thenReturn(advert);


        Result response = advertService.updateStatusActiveById(1);
        Mockito.verify(rabbitMqService).sendMessage(any());
        assertNotNull(response);
        assertEquals(true, response.isSuccess());

    }

    @Test
    void updateStatusPassiveByIdTest() {
        Advert advert = prepareAdvert(1);

        Mockito
                .when(advertDao.findById(1))
                .thenReturn(advert);


        Result response = advertService.updateStatusPassiveById(1);
        Mockito.verify(rabbitMqService).sendMessage(any());
        assertNotNull(response);
        assertEquals(true, response.isSuccess());

    }

    private DataResult<UserBalanceResponse> prepareUserBalanceResponse() {
        LocalDate localDate = LocalDate.of(2022, 5, 15);
        DataResult<UserBalanceResponse> response = new DataResult<UserBalanceResponse>(new UserBalanceResponse(1, 10, localDate), true);
        return response;
    }

    private DataResult<UserBalanceResponse> prepareUserBalanceResponseExpiredDate() {
        LocalDate localDate = LocalDate.of(2022, 2, 15);
        DataResult<UserBalanceResponse> response = new DataResult<UserBalanceResponse>(new UserBalanceResponse(1, 10, localDate), true);
        return response;
    }


    private UserBalanceRequest prepareUserBalanceRequest() {
        LocalDate localDate = LocalDate.now();
        return new UserBalanceRequest(1, 10, localDate);
    }


    private List<AdvertResponse> prepareMockAdvertResponseList() {
        List<AdvertResponse> advertResponseList = new ArrayList<AdvertResponse>();
        advertResponseList.add(prepareAdvertResponse(1));
        advertResponseList.add(prepareAdvertResponse(2));
        advertResponseList.add(prepareAdvertResponse(3));
        advertResponseList.add(prepareAdvertResponse(4));
        return advertResponseList;
    }

    private List<Advert> prepareMockAdvertList() {
        List<Advert> advertList = new ArrayList<Advert>();
        advertList.add(prepareAdvert(1));
        advertList.add(prepareAdvert(2));
        advertList.add(prepareAdvert(3));
        advertList.add(prepareAdvert(4));
        return advertList;
    }

    private Advert prepareAdvert(int id) {
        return new Advert(id, 1, "başlık", "açıklama", 2500., AdvertStatus.IN_REVIEW);

    }

    private AdvertRequest prepareAdvertRequest(int id) {
        return new AdvertRequest(id, 1, "başlık", "açıklama", 2500., AdvertStatus.IN_REVIEW);

    }

    private AdvertUpdateRequest prepareAdvertUpdateRequest(int id) {
        return new AdvertUpdateRequest(id, "başlık", "açıklama", 2500.);

    }

    private AdvertResponse prepareAdvertResponse(int id) {
        return new AdvertResponse(id, 1, "başlık", "açıklama", 2500., AdvertStatus.IN_REVIEW);

    }

}
