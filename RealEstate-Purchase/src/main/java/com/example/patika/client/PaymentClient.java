package com.example.patika.client;


import com.example.patika.client.request.PaymentRequest;
import com.example.patika.utils.results.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feign-purchase-to-payment", url = "http://localhost:8084")
public interface PaymentClient {


    @PostMapping("/payments")
    Result pay(@RequestBody PaymentRequest paymentRequest);


}
