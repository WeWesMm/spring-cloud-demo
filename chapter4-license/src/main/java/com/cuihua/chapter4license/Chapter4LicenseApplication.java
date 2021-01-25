package com.cuihua.chapter4license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lin Jing
 */
@SpringBootApplication(scanBasePackages = {"com.cuihua.chapter4license"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cuihua.chapter4license"})
public class Chapter4LicenseApplication {

    @LoadBalanced
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter4LicenseApplication.class, args);
    }

}
