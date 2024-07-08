package com.example.patika.client;

import com.example.patika.client.request.UserBalanceRequest;
import com.example.patika.client.response.IndividualUserResponse;

import com.example.patika.utils.results.DataResult;
import com.example.patika.utils.results.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "feign-purchase-to-individual-user", url = "http://localhost:8080")
public interface UserClient {


    @GetMapping(value = "/individual-users/{id}")
    DataResult<IndividualUserResponse> findById(@PathVariable int id);

    @PutMapping("/individual-users/{id}/update-balance")
    Result updateBalanceById(@PathVariable int id, @RequestBody UserBalanceRequest userBalanceRequest);

}
