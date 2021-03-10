package com.cuihua.chapter6zuulsvr;

import com.cuihua.chapter6zuulsvr.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
// 使服务成为一个 Zuul 服务器
@EnableZuulProxy
public class Chapter6ZuulsvrApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        List interceptor = template.getInterceptors();
        if (interceptor == null) {
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptor.add(new UserContextInterceptor());
            template.setInterceptors(interceptor);
        }

        return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter6ZuulsvrApplication.class, args);
    }

}
