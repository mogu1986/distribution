package com.mw.distribution.wxpay.pojo;

import com.mw.distribution.wxpay.pojo.base.BasePayRequest;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付-查询退款-请求.
 *
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class RefundQueryRequest extends BasePayRequest {

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号.
     */
    @XmlElement(name = "out_refund_no")
    private String outRefundNo;

    @XmlElement(name = "refund_id")
    private String refundId;

    @XmlElement(name = "offset")
    private Integer offset;

}
