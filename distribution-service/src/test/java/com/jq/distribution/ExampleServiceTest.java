package com.jq.distribution;


import com.jq.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class ExampleServiceTest {

    @Resource
    ExampleInnerService helloInnerService;

    @Test
    public void saveTest() {
        log.info("service . value : {}", this.helloInnerService);
    }
}
