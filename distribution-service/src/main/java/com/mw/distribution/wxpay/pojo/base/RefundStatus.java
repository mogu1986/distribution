package com.mw.distribution.wxpay.pojo.base;

/**
 * 退款状态.
 *
 */
public enum RefundStatus {

    /**
     * 退款成功.
     */
    SUCCESS,

    /**
     * 退款关闭.
     */
    REFUNDCLOSE,

    /**
     * 退款处理中.
     */
    PROCESSING,

    /**
     * 退款异常.
     */
    CHANGE
}
