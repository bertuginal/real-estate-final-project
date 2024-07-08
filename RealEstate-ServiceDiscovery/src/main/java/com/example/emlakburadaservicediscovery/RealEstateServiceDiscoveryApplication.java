package com.example.emlakburadaservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RealEstateServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateServiceDiscoveryApplication.class, args);
	}

}
