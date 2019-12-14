package com.mw.distribution.wxpay.exception;

/**
 * 微信支付异常.
 *
 */
public class WeChatPayException extends RuntimeException {
    public WeChatPayException(final String message) {
        super(message);
    }
}
