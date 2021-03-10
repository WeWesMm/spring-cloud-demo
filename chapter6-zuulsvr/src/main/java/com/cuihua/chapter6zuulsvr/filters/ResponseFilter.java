package com.cuihua.chapter6zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lin Jing
 * @date 2021/1/15 下午4:46
 */
@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 设置过滤器类型为后置过滤器
     *
     * @return post
     */
    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    /**
     * 返回一个整数值,指示不同类型的过滤器的执行顺序
     *
     * @return 执行顺序
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /**
     * 指示过滤器是否要执行
     *
     * @return 过滤器是否执行
     */
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();

        log.info("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

        log.info("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }
}
