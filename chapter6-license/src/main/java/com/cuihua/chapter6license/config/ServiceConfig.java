package com.cuihua.chapter6license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lin Jing
 * @date 2021/1/15 下午5:13
 */
@Component
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty = "";

    public String getExampleProperty() {
        return exampleProperty;
    }
}
