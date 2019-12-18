package com.mw.distribution.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: Example Service
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@FeignClient(value = "distribution",path = "/inner/distribution/example",url = "${feign-url.user:}")
public interface ExampleApi {

    /**
     * @Description: 回声测试
     * @author: jim
     * @date: 2019-04-23 16:14
     * @return String
     */
    @GetMapping(value = "echo")
    String echo(@RequestParam String echo);

}