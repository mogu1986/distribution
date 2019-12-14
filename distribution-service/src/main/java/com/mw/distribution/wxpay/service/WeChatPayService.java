package com.mw.distribution.wxpay.service;



import com.mw.distribution.wxpay.client.HttpClientFactory;
import com.mw.distribution.wxpay.client.WeChatPayClient;
import com.mw.distribution.wxpay.client.WeChatPayHttpComponentsClient;
import com.mw.distribution.wxpay.config.WeChatPayConfig;
import com.mw.distribution.wxpay.pojo.*;
import com.mw.distribution.wxpay.pojo.base.BasePayRequest;
import com.mw.distribution.wxpay.pojo.base.BasePayResponse;
import com.mw.distribution.wxpay.util.ObjectUtils;
import com.mw.distribution.wxpay.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;

import java.util.function.Function;

/**
 * @author lumeng
 * @Description: 下单接口
 * @Date: 2019/8/12 16:41.
 */
@Slf4j
public class WeChatPayService {

    private final WeChatPayClient client;

    /**
     * 查询退款.
     *
     * @param outTradeNo 商户订单号
     *
     * @return RefundQueryResponse
     */
    public RefundQueryResponse refundQuery(final String outTradeNo) {
        final RefundQueryRequest request = new RefundQueryRequest();
        request.setOutTradeNo(outTradeNo);
        return this.call(this.client::refundQuery, request);
    }

    /**
     * 查询退款.
     *
     * @param outTradeNo 商户订单号
     * @param offset 分页查询的偏移量,
     *         举例：当商户想查询第25笔时，可传入订单号及offset=24，微信支付平台会返回第25笔到第35笔的退款单信息.
     *
     * @return RefundQueryResponse
     */
    public RefundQueryResponse refundQuery(final String outTradeNo, final int offset) {
        final RefundQueryRequest request = new RefundQueryRequest();
        request.setOutTradeNo(outTradeNo);
        request.setOffset(offset);
        return this.call(this.client::refundQuery, request);
    }



    private WeChatPayClient weChatPayClient() {
        // 避免硬编码对 org.apache.http.client.HttpClient 的依赖
        final HttpClient httpClient = new HttpClientFactory( WeChatPayConfig.mchId, WeChatPayConfig.certificatePath).build();
        return new WeChatPayHttpComponentsClient(WeChatPayConfig.basePath, httpClient);
    }
    public WeChatPayService() {
        this.client = this.weChatPayClient();
    }

    /**
     *  统一处理 调用
     * @param fun
     * @param request
     * @param <T>
     * @param <R>
     * @return
     */
    private <T extends BasePayRequest, R extends BasePayResponse> R call(
            final Function<T, R> fun, final T request) {
        this.beforeRequest(request);
        final R response = fun.apply(request);
        ObjectUtils.checkSuccessful(response, WeChatPayConfig.mchKey);
        return response;
    }

    /**
     * 请求前去参数处理
     * @param request
     */
    private void beforeRequest(final BasePayRequest request) {
        this.configureAndSign(request);
    }

    /**
     * 请求签名
     * @param request
     */
    private void configureAndSign(final BasePayRequest request) {
        request.setAppId(WeChatPayConfig.appId);
        request.setMchId(WeChatPayConfig.mchId);
        request.setNonceStr(ObjectUtils.uuid32());
        request.setSign(SignUtils.generateSign(request, WeChatPayConfig.mchKey));
    }

}
