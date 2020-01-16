package com.jq.distribution.controller;

import com.jq.distribution.api.ExampleApi;
import com.jq.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: Example 的 Feign 入口类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/distribution/example")
@RefreshScope
public class ExampleController implements ExampleApi {

    @Value("${timeout:100}")
    private int timeout;

    @Value("${dubbo.config-center.address:no}")
    private String address;

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Value("${foo:null}")
    private String foo;

    @Resource
    ExampleInnerService exampleInnerService;

    @Override
    public String echo(@RequestParam String echo) {
        log.info("feign 调用的哦 {}", echo);
        return exampleInnerService.echo(echo);
    }

    @GetMapping("/nacos")
    public boolean nacos() {
        return this.useLocalCache;
    }

    @GetMapping("/foo")
    public String foo() {
        return foo;
    }

    @GetMapping("/core")
    public String core() {
        int core = Runtime.getRuntime().availableProcessors();
        return String.valueOf(core);
    }

    @GetMapping("/hello")
    public String hello() {
        log.info("[hello]");
        log.info("timeout = {}", this.timeout);
        log.info("address = {}", this.address);
        log.info("&%^&*$%^&$%^|||| /n  sfsfsf^&*^*");

        log.warn("this is warn log");

        try {
            int x = 1 / 0;
        } catch (Exception e) {
            log.error("error {}", e);
        }

        String hostname = System.getenv("HOSTNAME");
        String rs = this.exampleInnerService.echo(hostname);
        rs = rs + ": hi sxh";
        return rs;
    }

    @RequestMapping("/k8s/health")
    public String healthz(@RequestHeader("X-Custom-Header") String header, HttpServletResponse response) throws IOException {
        log.info("k8s action, header = {}", header);
        if ("jim".equals(header)) {
            response.setStatus(200);
        }
        return "sucess";
    }

}