package com.mw.distribution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Description: 启动类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Slf4j
@SpringBootApplication
//@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
        log.info("distribution server start...");
    }

}