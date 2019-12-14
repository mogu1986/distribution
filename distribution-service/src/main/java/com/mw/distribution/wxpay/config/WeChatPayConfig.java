package com.mw.distribution.wxpay.config;

import com.mw.distribution.wxpay.constant.WeChatPayURLConstant;
import lombok.Data;

/**
 * 微信支付-配置.
 *
 */
@Data
public class WeChatPayConfig {

    /**
     * 公众号 ID.
     */
    public static final String appId ="wxe587625561489caf";

    /**
     * 商户号 ID.
     */
    public static final  String mchId ="1553211021";

    /**
     * 商户号 key.
     */
    public static final String mchKey ="shixiangyiwei201908shixiangyiwei";

    /**
     * 支付结果通知 url.
     */
    public static final String paymentNotifyUrl ="http://branchsxhpay.shixh.com/wxpay/paymentnotify.json";

    /**
     * 退款结果通知 url.
     */
    public static final String refundNotifyUrl ="http://branchsxhpay.shixh.com/wxpay/refundnotify.json";

    /**
     * api 证书路径.
     */
//    public static final  String certificatePath ="D:\\1550555431_20190813_cert/apiclient_cert.p12";
    public static final  String certificatePath ="/opt/wxcert/apiclient_cert.p12";
    /**
     *支付超时时间 分钟 15
     */
    public static final String EXPIRE_TIME="15";

    /**
     * 微信支付 api base path.
     */
    public static final String basePath = WeChatPayURLConstant.BASE_PATH;



}
