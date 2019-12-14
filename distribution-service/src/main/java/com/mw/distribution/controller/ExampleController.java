package com.mw.distribution.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.mw.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Description: Example 的 Feign 入口类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@RestController
@RefreshScope
public class ExampleController {

    @Value("${timeout:100}")
    private int timeout;

    @Value("${dubbo.config-center.address:no}")
    private String address;

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

//    @NacosInjected
//    private NamingService namingService;

    @Resource
    ExampleInnerService exampleInnerService;

    @GetMapping("/nacos")
    public boolean nacos() {
        return this.useLocalCache;
    }

//    @RequestMapping(value = "/get", method = GET)
//    @ResponseBody
//    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
//        return namingService.getAllInstances(serviceName);
//    }

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