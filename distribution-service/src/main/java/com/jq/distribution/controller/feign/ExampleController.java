package com.jq.distribution.controller.feign;

import com.jq.distribution.api.ExampleApi;
import com.jq.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @Description: Example 的 Feign 入口类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/distribution/example")
public class ExampleController implements ExampleApi {

    @Resource
    ExampleInnerService exampleInnerService;

    @Override
    public String echo(@RequestParam String echo) {
        return exampleInnerService.echo(echo);
    }

}