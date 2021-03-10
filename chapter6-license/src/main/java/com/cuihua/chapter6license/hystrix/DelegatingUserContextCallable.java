package com.cuihua.chapter6license.hystrix;

import com.cuihua.chapter6license.utils.UserContext;
import com.cuihua.chapter6license.utils.UserContextHolder;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * @author Lin Jing
 * @date 2021/1/19 下午3:39
 */
public class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;

    private UserContext originalUserContext;

    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(userContext, "userContext cannot be null");
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    public DelegatingUserContextCallable(Callable<V> delegate) {
        this(delegate, UserContextHolder.getContext());
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);

        try{
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
