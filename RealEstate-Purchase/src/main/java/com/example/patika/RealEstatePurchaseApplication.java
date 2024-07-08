package com.example.patika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableEurekaClient
public class RealEstatePurchaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstatePurchaseApplication.class, args);
    }

}
