package com.cuihua.chapter4eurekasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Chapter4EurekasvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter4EurekasvrApplication.class, args);
    }

}
