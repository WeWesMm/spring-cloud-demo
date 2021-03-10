package com.cuihua.chapter6eurekasvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wewesmm
 * @date 2020年12月09日22:18:55
 */
@SpringBootApplication
@EnableEurekaServer
public class Chapter6EurekasvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter6EurekasvrApplication.class, args);
    }

}
