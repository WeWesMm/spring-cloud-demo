package com.cuihua.chapter5license.utils;

import org.springframework.util.Assert;

/**
 * @author Lin Jing
 * @date 2020/11/24 下午11:01
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static UserContext getContext() {
        UserContext context = userContext.get();

        if (context == null) {
            context = UserContextHolder.createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context) {

        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    public static final UserContext createEmptyContext() {
        return new UserContext();
    }
}
