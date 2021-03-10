package com.cuihua.chapter6zuulsvr.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Lin Jing
 * @date 2021/1/13 下午11:47
 */
@Slf4j
@Component
public class UserContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        // UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        // UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        // UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));

        log.info("Special Routes Service Incoming Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}