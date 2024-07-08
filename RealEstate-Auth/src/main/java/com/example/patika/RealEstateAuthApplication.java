package com.example.patika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RealEstateAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateAuthApplication.class, args);
    }

}
