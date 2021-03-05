package com.cuihua.chapter5license.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lin Jing
 * @date 2020/12/1 下午11:36
 */
@Configuration
public class ThreadLocalConfiguration {

    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;
    //
    // @PostConstruct
    // public void init() {
    //
    //     // 保留原有的 Hystrix 的所有插件
    //     HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
    //     HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
    //     HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
    //     HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
    //
    //     // 重置 Hystrix 的所有插件
    //     HystrixPlugins.reset();
    //
    //     // 注册自定义的并发策略
    //     HystrixPlugins.getInstance().registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
    //
    //     // 重新注册其他原有插件
    //     HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
    //     HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
    //     HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
    //     HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
    // }
}
