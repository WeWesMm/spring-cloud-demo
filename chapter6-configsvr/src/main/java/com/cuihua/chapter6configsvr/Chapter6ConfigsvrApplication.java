package com.cuihua.chapter6configsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class Chapter6ConfigsvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter6ConfigsvrApplication.class, args);
    }

}
