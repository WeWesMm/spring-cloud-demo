package com.cuihua.chapter5eurekasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Chapter5EurekasvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5EurekasvrApplication.class, args);
    }

}
