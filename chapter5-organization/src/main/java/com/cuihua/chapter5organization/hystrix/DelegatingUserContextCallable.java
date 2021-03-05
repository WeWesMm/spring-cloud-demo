package com.cuihua.chapter5organization.hystrix;

import com.cuihua.chapter5organization.util.UserContext;
import com.cuihua.chapter5organization.util.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * 线程类
 *
 * @author Lin Jing
 * @date 2020/12/7 下午10:19
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;
    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    public DelegatingUserContextCallable(Callable<V> delegate) {
        this(delegate, UserContextHolder.getContext());
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

    @Override
    public String toString() {
        return delegate.toString();
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}
