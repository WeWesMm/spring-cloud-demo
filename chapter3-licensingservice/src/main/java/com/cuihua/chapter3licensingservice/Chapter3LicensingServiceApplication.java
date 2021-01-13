package com.cuihua.chapter3licensingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Lin Jing
 */
@SpringBootApplication
@RefreshScope
public class Chapter3LicensingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter3LicensingServiceApplication.class, args);
    }

}
