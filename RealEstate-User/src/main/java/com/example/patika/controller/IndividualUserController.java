package com.example.patika.controller;

import com.example.patika.dto.request.IndividualUserRequest;
import com.example.patika.dto.request.UserBalanceRequest;
import com.example.patika.dto.response.IndividualUserResponse;
import com.example.patika.dto.response.UserBalanceResponse;
import com.example.patika.service.IndividiualUserService;
import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IndividualUserController {


    private IndividiualUserService individiualUserService;

    @Autowired
    public IndividualUserController(IndividiualUserService individiualUserService) {
        this.individiualUserService = individiualUserService;
    }

    @GetMapping("/individual-users")
    public ResponseEntity<DataResult<List<IndividualUserResponse>>> findAll() {
        DataResult<List<IndividualUserResponse>> result = individiualUserService.findAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/individual-users")
    public ResponseEntity<Result> add(@RequestBody IndividualUserRequest individualUserRequest) {
        Result result = individiualUserService.add(individualUserRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/individual-users/{id}")
    public ResponseEntity<Result> updateById(@PathVariable int id, @RequestBody IndividualUserRequest individualUserRequest) {
        Result result = individiualUserService.updateById(id, individualUserRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/individual-users/{id}")
    public ResponseEntity<Result> deleteById(@PathVariable int id) {
        Result result = individiualUserService.deleteById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/individual-users/{id}")
    public ResponseEntity<DataResult<IndividualUserResponse>> findById(@PathVariable int id) {
        DataResult<IndividualUserResponse> result = individiualUserService.findById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/individual-users/{id}/update-balance")
    public ResponseEntity<Result> updateBalanceById(@PathVariable int id, @RequestBody UserBalanceRequest userBalanceRequest) {
        Result result = individiualUserService.updateBalanceById(id, userBalanceRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/individual-users/{id}/find-balance")
    public ResponseEntity<DataResult<UserBalanceResponse>> findByIdForBalance(@PathVariable int id) {
        DataResult<UserBalanceResponse> result = individiualUserService.findByIdForBalance(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }


}
