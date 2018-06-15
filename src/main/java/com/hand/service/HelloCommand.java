package com.hand.service;

import com.netflix.hystrix.*;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ray Ma on 2018/6/7.
 */
public class HelloCommand extends HystrixCommand<String> {

    public static ConcurrentHashMap<String, Boolean> circuitBreakerMap = new ConcurrentHashMap();
    private String name;

    public HelloCommand() {
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("command"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().
                        withMetricsRollingStatisticalWindowInMilliseconds(3)
                        .withCoreSize(3)
                        .withMaxQueueSize(3)
                        .withKeepAliveTimeMinutes(5))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(2000))
        );
    }

    public HelloCommand(String name) {
        this();
        this.name = name;
    }

    @Override
    protected String run() throws InterruptedException {
        Boolean command = circuitBreakerMap.get("command");
        if (command != null && command) {
            throw new RuntimeException("服务不可用");
        }
        int i = RandomUtils.nextInt();
        if (i % 2 == 0) {
            return "Hello " + name + "!";
        } else {
            throw new RuntimeException("调用异常");
        }

    }

    @Override
    protected String getFallback() {
        Throwable executionException = this.getExecutionException();
        return "fail " + executionException;
    }

}
