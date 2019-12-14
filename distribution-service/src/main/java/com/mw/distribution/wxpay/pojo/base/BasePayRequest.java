package com.mw.distribution.wxpay.pojo.base;

import com.mw.distribution.wxpay.support.SignIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 微信支付-基本请求信息.
 */

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BasePayRequest {
    /**
     * 公众账号ID.
     */
    @XmlElement(name = "appid")
    private String appId;

    /**
     * 商户号.
     */
    @XmlElement(name = "mch_id")
    private String mchId;

    /**
     * 随机字符串.
     */
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名.
     */
    @SignIgnore
    @XmlElement(name = "sign")
    private String sign;

    /**
     * 签名类型.
     */
    @XmlElement(name = "sign_type")
    private SignType signType;

}
