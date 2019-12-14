package com.mw.distribution.wxpay.exception;

/**
 * 包装受检查异常.
 *
 */
public class UncheckedException extends RuntimeException {
    public UncheckedException(final Throwable cause) {
        super(cause);
    }

    public UncheckedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
