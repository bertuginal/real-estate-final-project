package com.example.patika.service;

import com.example.patika.dto.request.IndividualUserRequest;
import com.example.patika.dto.request.UserBalanceIdentificationRequest;
import com.example.patika.dto.request.UserBalanceRequest;
import com.example.patika.dto.response.IndividualUserResponse;
import com.example.patika.dto.response.UserBalanceResponse;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;

import java.util.List;

public interface IndividiualUserService {
    DataResult<List<IndividualUserResponse>> findAll();
    Result add(IndividualUserRequest individualUserRequest);
    Result updateById(int individualUserId,IndividualUserRequest individualUserRequest);
    Result deleteById(int individualUserId);
    DataResult<IndividualUserResponse> findById(int individualUserId);
    Result updateBalanceById(int individualUserId, UserBalanceRequest userBalanceRequest);
    Result updateBalanceByRabbitMq(int individualUserId, UserBalanceIdentificationRequest userBalanceIdentificationRequest);
    DataResult<UserBalanceResponse> findByIdForBalance(int individualUserId);
}
