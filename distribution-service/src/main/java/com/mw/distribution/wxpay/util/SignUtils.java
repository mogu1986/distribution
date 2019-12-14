package com.mw.distribution.wxpay.util;

import com.mw.distribution.wxpay.exception.UncheckedException;
import com.mw.distribution.wxpay.pojo.base.BasePayRequest;
import com.mw.distribution.wxpay.pojo.base.BasePayResponse;
import com.mw.distribution.wxpay.support.LocalDateTimeXmlAdapter;
import com.mw.distribution.wxpay.support.LocalDateXmlAdapter;
import com.mw.distribution.wxpay.support.SignIgnore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 签名工具.
 *
 * @author cn-src
 */
@Slf4j
public class SignUtils {
    private static final Map<Class, List<Field>> CACHE_FOR_SIGN = new ConcurrentHashMap<>();

    private SignUtils() {}

    /**
     * 微信支付-生成签名.
     *
     * @param request 要签名的数据对象.
     * @param mchKey mchKey
     *
     * @return 返回签名 String
     */

    public static String generateSign(
            final BasePayRequest request, final String mchKey) {

        return generateSign(signParamsFrom(request), mchKey);
    }

    /**
     * 微信支付-生成签名.
     *
     * @param response 要签名的数据对象.
     * @param mchKey mchKey
     *
     * @return 返回签名 String
     */

    public static String generateSign(
            final BasePayResponse response, final String mchKey) {
        final SortedMap<String, String> params = signParamsFrom(response);
        final SortedMap<String, String> otherParams = response.getOtherParams();
        if (null != otherParams && !otherParams.isEmpty()) {
            params.putAll(otherParams);
        }
        return generateSign(params, mchKey);
    }

    /**
     * 微信支付-生成签名.
     *
     * @param params 要签名的数据对象.
     * @param mchKey mchKey
     *
     * @return 返回签名 String
     */

    public static String generateSign(
            final SortedMap<String, String> params, final String mchKey) {

        final StringBuilder sb = new StringBuilder();

        for (final Map.Entry<String, String> entry : params.entrySet()) {
            if (null != entry.getKey() && !"".equals(entry.getKey())
                    && null != entry.getValue() && !"".equals(entry.getValue())) {
                sb.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
            }
        }
        sb.append("key").append('=').append(mchKey);
        log.info("key str"+ sb.toString());
        log.error("key str"+ sb.toString());
        return DigestUtils.md5Hex(sb.toString()).toUpperCase(Locale.ENGLISH);
    }

    private static SortedMap<String, String> signParamsFrom(final Object obj) {
        final Class<?> clazz = obj.getClass();
        final List<Field> fields = CACHE_FOR_SIGN.computeIfAbsent(clazz, clazz0 ->
                ObjectUtils.getFieldsListWithAnnotation(clazz0, XmlElement.class));
        ObjectUtils.checkNotEmpty(fields, "fields");

        final SortedMap<String, String> params = new TreeMap<>();
        try {
            for (final Field field : fields) {
                if (null != field.getAnnotation(SignIgnore.class)) {
                    continue;
                }
                Object value = field.get(obj);
                final XmlJavaTypeAdapter typeAdapter = field.getAnnotation(XmlJavaTypeAdapter.class);
                if (null != typeAdapter) {
                    if (LocalDateTimeXmlAdapter.class.equals(typeAdapter.value())) {
                        value = LocalDateTimeXmlAdapter.INSTANCE.marshal((LocalDateTime) value);
                    }
                    else if (LocalDateXmlAdapter.class.equals(typeAdapter.value())) {
                        value = LocalDateXmlAdapter.INSTANCE.marshal((LocalDate) value);
                    }
                }
                final String name = field.getAnnotation(XmlElement.class).name();
                final String valueStr = value == null ? null : value.toString();
                if (name.isEmpty()) {
                    continue;
                }
                if (null == valueStr || valueStr.isEmpty()) {
                    continue;
                }
                params.put(name, valueStr);
            }
        }
        catch (final IllegalAccessException e) {
            throw new UncheckedException(e);
        }
        return params;
    }


}
