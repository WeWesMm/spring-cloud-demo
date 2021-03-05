package com.cuihua.chapter5configsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Chapter5ConfigsvrApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5ConfigsvrApplication.class, args);
    }

}
