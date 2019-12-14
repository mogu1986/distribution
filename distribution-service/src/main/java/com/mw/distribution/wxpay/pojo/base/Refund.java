package com.mw.distribution.wxpay.pojo.base;

import lombok.Data;

import java.util.List;

/**
 * 退款信息.
 *
 */
@Data
public class Refund {

    /**
     * 商户退款单号.
     */
    private String outRefundNo;

    /**
     * 微信退款单号.
     */
    private String refundId;

    /**
     * 退款渠道.
     */
    private String refundChannel;

    /**
     * 申请退款金额.
     */
    private Integer refundFee;

    /**
     * 退款金额.
     */
    private Integer settlementRefundFee;

    /**
     * 总代金券退款金额.
     */
    private Integer couponRefundFee;

    /**
     * 退款代金券使用数量.
     */
    private Integer couponRefundCount;

    /**
     * 退款状态.
     */
    private RefundStatus refundStatus;

    /**
     * 退款资金来源.
     */
    private String refundAccount;

    /**
     * 退款入账账户.
     */
    private String refundRecvAccout;

    /**
     * 退款成功时间.
     */
    private String refundSuccessTime;

    private List<Coupon> coupons;

}
