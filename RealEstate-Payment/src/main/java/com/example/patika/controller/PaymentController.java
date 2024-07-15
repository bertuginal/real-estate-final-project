package com.example.patika.controller;

import com.example.patika.dto.request.PaymentRequest;
import com.example.patika.dto.response.PaymentResponse;
import com.example.patika.service.PaymentService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {


    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    // Ödeme yapma işlemi
    @PostMapping("/payments")
    public ResponseEntity<Result> pay(@RequestBody PaymentRequest paymentRequest) {
        Result result = paymentService.pay(paymentRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    // Ödeme detaylarının listelenmesi işlemi
    @GetMapping("/payments")
    public ResponseEntity<DataResult<List<PaymentResponse>>> findAll() {
        DataResult<List<PaymentResponse>> result= paymentService.findAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }


}
