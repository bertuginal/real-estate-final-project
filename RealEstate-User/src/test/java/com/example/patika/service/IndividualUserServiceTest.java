package com.example.patika.service;

import com.example.patika.dto.request.IndividualUserRequest;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.dto.request.UserBalanceRequest;
import com.example.patika.dto.response.IndividualUserResponse;
import com.example.patika.dto.response.UserBalanceResponse;
import com.example.patika.model.IndividualUser;
import com.example.patika.repository.IndividiualUserDao;
import com.example.patika.service.impl.IndividualUserServiceImpl;
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
public class IndividualUserServiceTest {

    @InjectMocks
    private IndividualUserServiceImpl individualUserService;

    @Mock
    private IndividiualUserDao individiualUserDao;

    @Mock
    private DtoConverterService dtoConverterService;

    @BeforeEach
    void setup() {

        Mockito.when(dtoConverterService.entityToDto(prepareMockUserList(),IndividualUserResponse.class))
                .thenReturn(prepareMockUserResponseList());

        Mockito.when(dtoConverterService.dtoToEntity(prepareUserRequest(1),IndividualUser.class))
                .thenReturn(prepareUser(1));

        Mockito.when(dtoConverterService.entityToDtoSingle(prepareUser(1),IndividualUserResponse.class))
                .thenReturn(prepareUserResponse(1));


        Mockito
                .when(individiualUserDao.findAll())
                .thenReturn(prepareMockUserList());


        Mockito
                .when(individiualUserDao.save(any()))
                .thenReturn(prepareUser(1));

    }


    @Test
    void findAll() {

        DataResult<List<IndividualUserResponse>> allUser = individualUserService.findAll();

        assertNotNull(allUser);

        assertThat(allUser.getData().size()).isNotZero();
    }


    @Test
    void saveUser() {

        Result response =individualUserService.add(prepareUserRequest(1));
        assertNotNull(response);
        assertEquals(true,response.isSuccess());

    }

    @Test
    void updateUser() {

        IndividualUserRequest request = prepareUserRequest(1);
        IndividualUser individualUser =prepareUser(1);

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);


        assertDoesNotThrow(() -> {
            Result response = individualUserService.updateById(1,request);

            assertEquals(true,response.isSuccess());

        });



    }

    @Test
    void deleteUserById(){

        IndividualUser individualUser =prepareUser(1);

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);

        assertDoesNotThrow(() -> {
            Result response = individualUserService.deleteById(1);
            Mockito.verify(individiualUserDao).deleteById(any());
            assertEquals(true,response.isSuccess());

        });

    }

    @Test
    void findByIdTest() {
        IndividualUser individualUser =prepareUser(1);

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);


        DataResult<IndividualUserResponse> response = individualUserService.findById(1);
        assertNotNull(response);
        assertEquals("mail", response.getData().getEmail());

    }

    @Test
    void  updateBalanceByIdTest() {
        IndividualUser individualUser =prepareUser(1);

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);




        assertDoesNotThrow(() -> {
            Result response =  individualUserService.updateBalanceById(1,prepareUserBalanceRequest());
            assertNotNull(response);
            assertEquals(true,response.isSuccess());
        });


    }


    @Test
    void  updateBalanceByRabbitMqTest() {
        IndividualUser individualUser =prepareUser(1);
        prepareUserBalanceRequest();

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);



            Result response =  individualUserService.updateBalanceByRabbitMq(1,prepareUserBalanceIdentificationRequest());
            assertNotNull(response);
            assertEquals(true,response.isSuccess());

    }

    @Test
    void findByIdForBalanceTest() {
        IndividualUser individualUser =prepareUser(1);

        Mockito
                .when(individiualUserDao.findById(1))
                .thenReturn(individualUser);

        Mockito.when(dtoConverterService.entityToDtoSingle(prepareUser(1), UserBalanceResponse.class))
                .thenReturn(prepareUserBalanceResponse());

        DataResult<UserBalanceResponse> response = individualUserService.findByIdForBalance(1);
        assertNotNull(response);
        assertEquals(10, response.getData().getAdvertBalance());

    }

    private UserBalanceResponse prepareUserBalanceResponse() {
        LocalDate localDate = LocalDate.of(2022, 5, 15);
        return  new UserBalanceResponse(1,10,localDate);
    }

    private UserBalanceIdentificationRequest prepareUserBalanceIdentificationRequest() {

        return new UserBalanceIdentificationRequest(1,1,10,30);
    }

    private UserBalanceRequest prepareUserBalanceRequest() {
        LocalDate localDate = LocalDate.now();
       return new UserBalanceRequest(1,10,localDate);
    }


    private List<IndividualUserResponse> prepareMockUserResponseList() {
        List<IndividualUserResponse> userList = new ArrayList<IndividualUserResponse>();
        userList.add(prepareUserResponse(1));
        userList.add(prepareUserResponse(2));
        userList.add(prepareUserResponse(3));
        userList.add(prepareUserResponse(4));
        return userList;
    }

    private List<IndividualUser> prepareMockUserList() {
        List<IndividualUser> userList = new ArrayList<IndividualUser>();
        userList.add(prepareUser(1));
        userList.add(prepareUser(2));
        userList.add(prepareUser(3));
        userList.add(prepareUser(4));
        return userList;
    }

    private IndividualUser prepareUser(int id) {
        return new IndividualUser(id,"mail","123456","url","555555","yunus","koç");

    }

    private IndividualUserRequest prepareUserRequest(int id) {
        return new IndividualUserRequest(id,"mail","123456","url","555555","yunus","koç");

    }

    private IndividualUserResponse prepareUserResponse(int id) {
        return new IndividualUserResponse(id,"mail","123456","url","555555","yunus","koç");

    }
}
