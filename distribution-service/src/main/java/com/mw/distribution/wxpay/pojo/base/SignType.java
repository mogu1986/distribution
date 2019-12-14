package com.mw.distribution.wxpay.pojo.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * 签名类型.
 *
 */
@XmlType
@XmlEnum
public enum SignType {
    @XmlEnumValue("MD5") MD5("MD5"),
    @XmlEnumValue("HMAC-SHA256") HMAC_SHA256("HMAC-SHA256");
    private final String value;

    SignType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
