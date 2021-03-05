package com.cuihua.chapter5license.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Lin Jing
 * @date 2020/12/1 上午12:20
 */
@Slf4j
@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // 检索调用的 HTTP 头部中设置的值，将这些值赋给存储在 UserContextHolder 中的 UserContext
        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));

        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));

        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));

        UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));

        log.info("UserContext Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
