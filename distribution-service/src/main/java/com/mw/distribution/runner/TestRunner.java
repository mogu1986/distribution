package com.mw.distribution.runner;

import com.mw.distribution.wxpay.pojo.RefundQueryResponse;
import com.mw.distribution.wxpay.service.WeChatPayService;
import com.qcloud.dts.context.NetworkEnv;
import com.qcloud.dts.context.SubscribeContext;
import com.qcloud.dts.message.ClusterMessage;
import com.qcloud.dts.message.DataMessage;
import com.qcloud.dts.subscribe.ClusterListener;
import com.qcloud.dts.subscribe.DefaultSubscribeClient;
import com.qcloud.dts.subscribe.SubscribeClient;
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

//        test();
    }

    private void test() throws Exception {
        System.out.println("dts tart");
        //创建一个 context
        SubscribeContext context = new SubscribeContext();
        //用户 secretId、secretKey
        context.setSecretId("AKID3pQE52oSpyrUmqrFKVQGUTDiyynQA41j");
        context.setSecretKey("cy4xc1B9wVpyyrHSYP0MXWDk186ICuZD");
        // 设置 channel 所在的 region，2.8.0以后的 SDK 必须设置 region 参数
        // region 值参照：https://cloud.tencent.com/document/product/236/15833#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8
        context.setRegion("ap-beijing");
        // 订阅的 serviceIp 和 servicePort
        // 注意：2.8.0以前的 SDK 需要设置 IP 和 Port 两个参数，2.8.0以后的版本如果设置了 region 参数则可以省略
        context.setServiceIp("172.16.0.15");
        context.setServicePort(7507);

//        context.setChannelId("dts-channel-SaV7v6l8GM9L7C3d");
        // 如果运行 SDK 的 CVM 不能访问外网，设置网络环境为内网;默认为外网。
        context.setNetworkEnv(NetworkEnv.WAN);
        //创建客户端
        SubscribeClient client = new DefaultSubscribeClient(context);
        //创建订阅 listener
        ClusterListener listener = new ClusterListener() {
            @Override
            public void notify(List<ClusterMessage> messages) throws Exception {
                //消费订阅到的数据
                for (ClusterMessage m : messages) {
                    for (DataMessage.Record.Field f : m.getRecord().getFieldList()) {
                        if (f.getFieldname().equals("id")) {
                            System.out.println("seq:" + f.getValue());
                        }
                        DataMessage.Record record = m.getRecord();
                    }
                    //消费完之后，确认消费
                    m.ackAsConsumed();
                }
            }

            @Override
            public void onException(Exception e) {
                System.out.println("listen exception" + e);
            }
        };
        //添加监听者
        client.addClusterListener(listener);
        //设置请求的订阅通道
        client.askForGUID("dts-channel-SaV7v6l8GM9L7C3d");
        //启动客户端
        client.start();
    }
}
