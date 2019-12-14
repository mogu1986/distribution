package com.mw.distribution.wxpay.pojo.base;

import lombok.Data;

/**
 * 代金券.
 *
 */
@Data
public class Coupon {

    private String id;
    private Type type;
    private Integer fee;

    /**
     * 代金券类型.
     */
    public enum Type {
        /**
         * 充值代金券.
         */
        CASH,
        /**
         * 非充值代金券.
         */
        NO_CASH
    }
}