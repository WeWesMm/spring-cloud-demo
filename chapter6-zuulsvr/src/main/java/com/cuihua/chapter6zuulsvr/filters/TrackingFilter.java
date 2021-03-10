package com.cuihua.chapter6zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lin Jing
 * @date 2020/12/24 下午11:12
 */
@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 告诉 Zuul, 该过滤器是前置过滤器、路由过滤器还是后置过滤器
     *
     * @return 过滤器类型
     */
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
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
     * 指使过滤器是否要执行
     *
     * @return 过滤器是否执行
     */
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    /**
     * 每次服务通过过滤器执行的代码
     * 检查 tmx-correlation-id 是否存在, 如果不存在, 则生成一个关联键, 并设置 HTTP 首部 tmx-correlation-id
     *
     * @return null
     * @throws ZuulException 异常
     */
    @Override
    public Object run() throws ZuulException {
        if (isCorrelationIdPresent()) {
            log.info("tmx-correlation-id found in tracking filter: {}.", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            log.info("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }

    /**
     * 判断 tmx-correlation-id 是否存在
     *
     * @return tmx-correlation-id 是否存在
     */
    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }

    /**
     * 生成关联 ID 的 GUID 值
     *
     * @return GUID
     */
    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
