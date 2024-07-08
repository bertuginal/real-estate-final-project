package com.example.patika.service;


import com.example.patika.dto.request.PaymentRequest;
import com.example.patika.dto.response.PaymentResponse;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;

import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentResponse>> findAll();
    Result pay(PaymentRequest paymentRequest);

}
