package com.mw.distribution.controller;

import com.mw.distribution.api.ExampleApi;
import com.mw.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Description: Example 的 Feign 入口类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@RestController
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

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String echo(@RequestParam String string) {
        return exampleInnerService.echo(string);
    }

    @GetMapping("/nacos")
    public boolean nacos() {
        return this.useLocalCache;
    }

    @GetMapping("/foo")
    public String foo() {
        return foo;
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