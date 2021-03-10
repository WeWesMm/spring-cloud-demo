package com.cuihua.chapter6license.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author Lin Jing
 * @date 2021/1/14 上午12:07
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    /**
     * 该方法在 RestTemplate 发生实际的 HTTP 服务调用之前被调用
     *
     * @param httpRequest
     * @param body
     * @param clientHttpRequestExecution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        HttpHeaders headers = httpRequest.getHeaders();
        // 为传出服务调用准备 HTTP 请求首部，并添加存储在 UserContext 中的关联 ID
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        return clientHttpRequestExecution.execute(httpRequest, body);
    }
}
