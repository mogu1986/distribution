package com.jq.distribution;

import com.jq.boot.feign.FeignConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: 启动类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@EnableFeignClients(basePackages = {"com.jq"}, defaultConfiguration= FeignConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
        log.info("distribution server start...");
    }

}