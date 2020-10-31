package com.jq.distribution.service.impl;

import com.jq.distribution.service.ExampleInnerService;
import com.jq.order.api.OrderApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: Example Service 实现类
 * @Date: 2019-04-23 16:14
 * @Author: jim
 */
@Service
@Slf4j
public class ExampleServiceImpl implements ExampleInnerService {

    @Resource
    OrderApi orderApi;

    /**
     * @Description: 回声测试
     * @author: jim
     * @date: 2019-04-23 16:14
     * @return String
     */
    @Override
    public String echo(String echo) {
        this.orderApi.echo(echo);
        return "echo : " + echo;
    }

}