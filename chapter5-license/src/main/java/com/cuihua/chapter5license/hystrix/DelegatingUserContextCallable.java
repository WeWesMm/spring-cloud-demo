package com.cuihua.chapter5license.hystrix;

import com.cuihua.chapter5license.utils.UserContext;
import com.cuihua.chapter5license.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * @author Lin Jing
 * @date 2020/12/1 下午11:14
 */
public final class DelegatingUserContextCallable<V> implements Callable<V> {

    /**
     * 原始 Callable 类
     */
    private final Callable<V> delegate;
    /**
     * 来自父类的 UserContext
     */
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    /**
     * 自定义 Callable 类的 call 方法在被 @HystrixCommand 注解保护的方法之前调用
     *
     * @return 被 HystrixCommand 修饰的方法返回
     * @throws Exception
     */
    @Override
    public V call() throws Exception {
        // 设置 UserContext，存储 UserContext 的 ThreadLocal 变量和运行受 Hystrix 保护的方法的线程相关联
        UserContextHolder.setContext(originalUserContext);
        try {
            // UserContext 设置之后
            // 在 Hystrix 保护的方法上调用 call() 方法
            // 如 LicenseService.getLicenseByOrg() 方法
            return delegate.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}
