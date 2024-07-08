package com.example.patika.client;


import com.example.patika.client.response.AdvertResponse;
import com.example.patika.utils.results.DataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feign-advertstatus-to-advert", url = "http://localhost:8081")
public interface AdvertClient {


    @GetMapping("/adverts/{id}")
    DataResult<AdvertResponse> findById(@PathVariable int id);


}
