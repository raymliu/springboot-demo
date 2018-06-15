package com.hand.web;

import com.hand.service.HelloCommand;
import com.hand.service.HystrixMoniterService;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ray Ma on 2018/6/7.
 */
@RestController
@RequestMapping("/api/hystrix")
public class HystrixController {

    @Autowired
    HystrixMoniterService hystrixMoniterService;


    /**
     * Hystrix 管控调用
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/by/name",method = RequestMethod.GET)
    public String test1() throws ExecutionException, InterruptedException {
        return new HelloCommand("hello").queue().get();
    }

    /**
     * 监视器
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/monitor",method = RequestMethod.GET)
    public String test2() throws ExecutionException, InterruptedException {
        HystrixCommandMetrics hystrixMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey(HelloCommand.class.getSimpleName()));
        return hystrixMoniterService.getStatsStringFromMetrics(hystrixMetrics);
    }

    /**
     * 模拟熔断器
     * @param enabled
     */
    @RequestMapping(value = "/circuit/breaker/shift",method = RequestMethod.GET)
    public void test3(@RequestParam("enabled")boolean enabled){
        HelloCommand.circuitBreakerMap.put("command",enabled);
    }

}
