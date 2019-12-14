package com.mw.distribution.wxpay.pojo;



import com.mw.distribution.wxpay.pojo.base.BasePayResponse;
import com.mw.distribution.wxpay.pojo.base.Coupon;
import com.mw.distribution.wxpay.pojo.base.Refund;
import com.mw.distribution.wxpay.pojo.base.RefundStatus;
import com.mw.distribution.wxpay.support.SignIgnore;
import com.mw.distribution.wxpay.util.ObjectUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 微信支付-查询退款-响应.
 *
 */
@Getter
@Setter
@ToString(callSuper = true, exclude = {"refunds"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class RefundQueryResponse extends BasePayResponse {

    @XmlElement(name = "total_refund_count")
    private Integer totalRefundCount;

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    @XmlElement(name = "total_fee")
    private Integer totalFee;

    @XmlElement(name = "settlement_total_fee")
    private Integer settlementTotalFee;

    @XmlElement(name = "fee_type")
    private String feeType;

    @XmlElement(name = "cash_fee")
    private Integer cashFee;

    @XmlElement(name = "refund_count")
    private Integer refundCount;

    @SignIgnore
    private List<Refund> refunds;

    @Override
    public void subProcessResponse() {
        if (null == this.refunds && null != this.otherParams) {
            final Map<String, Refund> refundsMap = ObjectUtils.beansMapFrom(this.otherParams, createRefundMapping(), Refund::new);
            initCoupons(refundsMap);
            this.refunds = new ArrayList<>(refundsMap.values());
        }
    }

    private void initCoupons(final Map<String, Refund> refundMap) {
        for (final Map.Entry<String, Refund> entry : refundMap.entrySet()) {
            final String key = entry.getKey();
            final List<Coupon> coupons =ObjectUtils.beansFrom(this.otherParams, createCouponMapping(key), Coupon::new);
            entry.getValue().setCoupons(coupons);
        }
    }


    private Map<String, BiConsumer<String, Coupon>> createCouponMapping(final String key) {
        final Map<String, BiConsumer<String, Coupon>> couponMapping = new HashMap<>(3);
        couponMapping.put("coupon_type_" + key + "_", (val, coupon) -> coupon.setType(Coupon.Type.valueOf(val)));
        couponMapping.put("coupon_refund_id_" + key + "_", (val, coupon) -> coupon.setId(val));
        couponMapping.put("coupon_refund_fee_" + key + "_", (val, coupon) -> coupon.setFee(Integer.valueOf(val)));
        return couponMapping;
    }


    private Map<String, BiConsumer<String, Refund>> createRefundMapping() {
        final Map<String, BiConsumer<String, Refund>> refundMapping = new HashMap<>(11);
        refundMapping.put("out_refund_no_", (val, coupon) -> coupon.setOutRefundNo(val));
        refundMapping.put("refund_id_", (val, coupon) -> coupon.setRefundId(val));
        refundMapping.put("refund_channel_", (val, coupon) -> coupon.setRefundChannel(val));
        refundMapping.put("refund_fee_", (val, coupon) -> coupon.setRefundFee(Integer.valueOf(val)));
        refundMapping.put("settlement_refund_fee_",
                (val, coupon) -> coupon.setSettlementRefundFee(Integer.valueOf(val)));
        refundMapping.put("coupon_refund_fee_", (val, coupon) -> coupon.setCouponRefundFee(Integer.valueOf(val)));
        refundMapping.put("coupon_refund_count_", (val, coupon) -> coupon.setCouponRefundCount(Integer.valueOf(val)));
        refundMapping.put("refund_status_", (val, coupon) ->
                coupon.setRefundStatus(ObjectUtils.enumOf(val, RefundStatus.class)));
        refundMapping.put("refund_account_", (val, coupon) -> coupon.setRefundAccount(val));
        refundMapping.put("refund_recv_accout_", (val, coupon) -> coupon.setRefundRecvAccout(val));
        refundMapping.put("refund_success_time_", (val, coupon) -> coupon.setRefundSuccessTime(val));
        return refundMapping;
    }

}
