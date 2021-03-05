package com.cuihua.chapter5organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Chapter5OrganizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5OrganizationApplication.class, args);
    }

}
