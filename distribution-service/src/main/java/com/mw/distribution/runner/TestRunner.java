package com.mw.distribution.runner;

import com.mw.distribution.wxpay.pojo.RefundQueryResponse;
import com.mw.distribution.wxpay.service.WeChatPayService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author gaowei
 * @Description: 启动后执行
 * @Date: 2019/8/26 10:56.
 */
@Component
public class TestRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
//        WeChatPayService weChatPayService = new WeChatPayService();
//        System.out.println("---------------------start--------------------------》》》");
//        RefundQueryResponse refundQueryResponse = weChatPayService.refundQuery("1908246148529624773754882407");
//        System.out.println(refundQueryResponse.toString());
        System.out.println("---------------------end--------------------------》》》");

    }

}
