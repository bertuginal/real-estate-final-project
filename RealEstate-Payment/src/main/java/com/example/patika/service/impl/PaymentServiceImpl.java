package com.example.patika.service.impl;


import com.example.patika.dto.request.PaymentRequest;
import com.example.patika.dto.response.PaymentResponse;
import com.example.patika.exception.AmountTypeException;
import com.example.patika.model.Payment;
import com.example.patika.repository.PaymentDao;
import com.example.patika.service.PaymentService;
import com.example.patika.utils.dtoConverter.DtoConverterService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import com.example.patika.utils.results.SuccessDataResult;
import com.example.patika.utils.results.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;
    private DtoConverterService dtoConverterService;

    @Autowired
    public PaymentServiceImpl(PaymentDao paymentDao, DtoConverterService dtoConverterService) {
        this.paymentDao = paymentDao;
        this.dtoConverterService = dtoConverterService;
    }

    @Override
    public DataResult<List<PaymentResponse>> findAll() {
        return new SuccessDataResult<List<PaymentResponse>>(dtoConverterService.entityToDto(paymentDao.findAll(), PaymentResponse.class),
                "Payments have been listed!");
    }

    @Override
    public Result pay(PaymentRequest paymentRequest) {

        if(paymentRequest.getAmount()<0){
            throw new AmountTypeException("Amount type error!");
        }

        paymentDao.save((Payment) dtoConverterService.dtoToEntity(paymentRequest, Payment.class));
        return new SuccessResult("Payment successful!");
    }


}
