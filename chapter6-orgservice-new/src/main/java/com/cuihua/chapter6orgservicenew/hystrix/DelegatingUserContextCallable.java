package com.cuihua.chapter6orgservicenew.hystrix;

import com.cuihua.chapter6orgservicenew.utils.UserContext;
import com.cuihua.chapter6orgservicenew.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * @author Lin Jing
 * @date 2021/1/22 下午2:20
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext originalUserContext) {
        this.delegate = delegate;
        this.originalUserContext = originalUserContext;
    }

    public DelegatingUserContextCallable(Callable<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);

        try {
            return delegate.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    private static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}
