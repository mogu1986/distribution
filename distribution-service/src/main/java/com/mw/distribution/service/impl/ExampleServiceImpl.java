package com.mw.distribution.service.impl;

import com.mw.distribution.service.ExampleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: Example Service 实现类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Service
@Slf4j
public class ExampleServiceImpl implements ExampleInnerService {

    /**
     * @Description: 回声测试
     * @author: jim
     * @date: 2019-04-23 16:14
     * @return String
     */
    @Override
    public String echo(String echo) {
        return "echo : " + echo;
    }

}