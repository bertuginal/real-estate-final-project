package com.example.patika.controller;

import com.example.patika.dto.request.PurchaseRequest;
import com.example.patika.dto.request.PurchaseStatusRequest;
import com.example.patika.dto.response.PurchaseResponse;
import com.example.patika.service.PurchaseService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchases")
    public ResponseEntity<DataResult<List<PurchaseResponse>>> findAll() {
        DataResult<List<PurchaseResponse>> result= purchaseService.findAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/purchases")
    public ResponseEntity<Result> purchase(@RequestBody PurchaseRequest purchaseRequest,@RequestHeader(value = "id") int userId) {
        Result result = purchaseService.purchase(purchaseRequest,userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<Result> updatePurchaseStatusById(@PathVariable int id, @RequestBody PurchaseStatusRequest purchaseStatusRequest){
        Result result = purchaseService.updatePurchaseStatusById(id,purchaseStatusRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/purchases/{id}")
    public ResponseEntity<DataResult<PurchaseResponse>> findById(@PathVariable int id) {
        DataResult<PurchaseResponse> result = purchaseService.findById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/purchases/individual-user/{userId}")
    public ResponseEntity<DataResult<List<PurchaseResponse>>> findAllByUserId(@PathVariable int userId) {
        DataResult<List<PurchaseResponse>> result= purchaseService.findAllByUserId(userId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
