package com.cuihua.chapter6organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class Chapter6OrganizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter6OrganizationApplication.class, args);
    }

}
