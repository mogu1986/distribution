package com.mw.distribution.wxpay.support;

import java.lang.annotation.*;

/**
 * 标注不参与签名计算的字段.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignIgnore {
}
