package com.example.patika.service.impl;

import com.example.patika.dto.request.IndividualUserRequest;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.dto.request.UserBalanceRequest;
import com.example.patika.dto.response.IndividualUserResponse;
import com.example.patika.dto.response.UserBalanceResponse;
import com.example.patika.exception.UserNotFoundException;
import com.example.patika.model.IndividualUser;
import com.example.patika.repository.IndividiualUserDao;
import com.example.patika.service.IndividiualUserService;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import com.example.patika.utils.results.SuccessDataResult;
import com.example.patika.utils.results.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualUserServiceImpl implements IndividiualUserService {

    private IndividiualUserDao individiualUserDao;
    private DtoConverterService dtoConverterService;

    @Autowired
    public IndividualUserServiceImpl(IndividiualUserDao individiualUserDao, DtoConverterService dtoConverterService) {
        this.individiualUserDao = individiualUserDao;
        this.dtoConverterService = dtoConverterService;
    }


    @Override
    public DataResult<List<IndividualUserResponse>> findAll() {

        return new SuccessDataResult<List<IndividualUserResponse>>(dtoConverterService.entityToDto(individiualUserDao.findAll(), IndividualUserResponse.class),
                "All users have been listed!");

    }

    // User oluşturma işlemi
    @Override
    public Result add(IndividualUserRequest individualUserRequest) {

        individiualUserDao.save((IndividualUser) dtoConverterService.dtoToEntity(individualUserRequest, IndividualUser.class));

        return new SuccessResult("User has been successfully added!");
    }

    // Kullanıcının id'sine göre user güncelleme işlemi
    @Override
    public Result updateById(int individualUserId, IndividualUserRequest individualUserRequest) {

        if (individiualUserDao.findById(individualUserId) == null) {
            throw new UserNotFoundException("User not found!");
        }
        IndividualUser individualUser = individiualUserDao.findById(individualUserId);
        //IndividualUser individualUser = (IndividualUser) dtoConverterService.dtoToEntity(individualUserRequest, IndividualUser.class);

        prepareIndividualUser(individualUser,individualUserRequest);

        individiualUserDao.save(individualUser);

        return new SuccessResult("User has been successfully updated!");
    }

    // Kullanıcının id'sine göre user silme işlemi
    @Override
    public Result deleteById(int individualUserId) {
        if (individiualUserDao.findById(individualUserId) == null) {
            throw new UserNotFoundException("User not found!");
        }

        individiualUserDao.deleteById(individualUserId);
        return new SuccessResult("User has been successfully deleted!");
    }

    // Kullanıcının id'sine göre user listeleme işlemi
    @Override
    public DataResult<IndividualUserResponse> findById(int individualUserId) {

        if (individiualUserDao.findById(individualUserId) == null) {
            throw new UserNotFoundException("User not found!");
        }
        return new SuccessDataResult<IndividualUserResponse>((IndividualUserResponse) dtoConverterService.entityToDtoSingle(individiualUserDao.findById(individualUserId), IndividualUserResponse.class),
                "User has been found!");
    }

    // Kullanıcının id'sine göre user bakiyesi güncelleme işlemi
    @Override
    public Result updateBalanceById(int individualUserId, UserBalanceRequest userBalanceRequest) {

        if (individiualUserDao.findById(individualUserId) == null) {
            throw new UserNotFoundException("User not found!");
        }
        IndividualUser individualUser = individiualUserDao.findById(individualUserId);
        individualUser.setAdvertBalance(userBalanceRequest.getAdvertBalance());
        individualUser.setEndDateOfPackage(userBalanceRequest.getEndDateOfPackage());

        individiualUserDao.save(individualUser);

        return new SuccessResult("Balance has been successfully updated!");
    }

    // Kullanıcının id'sine göre user bakiyesi güncelleme işlemi (rabbitMQ ile asenkron)
    @Override
    public Result updateBalanceByRabbitMq(int individualUserId, UserBalanceIdentificationRequest userBalanceIdentificationRequest) {


        IndividualUser individualUser = individiualUserDao.findById(individualUserId);

        individualUser.setId(individualUserId);
        individualUser.setAdvertBalance(userBalanceIdentificationRequest.getAdvertBalance());
        individualUser.setEndDateOfPackage(individualUser.getEndDateOfPackage().plusDays(userBalanceIdentificationRequest.getAdditionalDayEndDateOfPackage()));
        individiualUserDao.save(individualUser);
        return new SuccessResult("Balance has been successfully updated!");
    }

    // Kullanıcının id'sine göre bakiye sorgulama işlemi
    @Override
    public DataResult<UserBalanceResponse> findByIdForBalance(int individualUserId) {
        if (individiualUserDao.findById(individualUserId) == null) {
            throw new UserNotFoundException("User not found!");
        }
        return new SuccessDataResult<UserBalanceResponse>((UserBalanceResponse) dtoConverterService.entityToDtoSingle(individiualUserDao.findById(individualUserId), UserBalanceResponse.class),
                "Balance has been listed by user id!");
    }

    private IndividualUser  prepareIndividualUser(IndividualUser individualUser,IndividualUserRequest individualUserRequest) {
        individualUser.setFirstName(individualUserRequest.getFirstName());
        individualUser.setLastName(individualUserRequest.getLastName());
        individualUser.setEmail(individualUserRequest.getEmail());
        individualUser.setPassword(individualUserRequest.getPassword());
        individualUser.setPhoto(individualUserRequest.getPhoto());
        individualUser.setPhoneNumber(individualUserRequest.getPhoneNumber());
        return individualUser;
    }

}
