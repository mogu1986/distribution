package com.mw.distribution.wxpay.client;


import com.mw.distribution.wxpay.pojo.*;

/**
 * 微信支付客户端接口.
 *
 */
public interface WeChatPayClient {

    /**
     * 查询退款.
     *
     * @param request RefundQueryRequest
     *
     * @return RefundQueryResponse
     */
    RefundQueryResponse refundQuery(RefundQueryRequest request);

}