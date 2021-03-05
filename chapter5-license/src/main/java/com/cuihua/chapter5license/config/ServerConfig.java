package com.cuihua.chapter5license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lin Jing
 * @date 2020/10/23 10:23
 */
@Component
public class ServerConfig {

    @Value("${example.property}")
    private String exampleProperty = "";

    public String getExampleProperty() {
        return exampleProperty;
    }

}
