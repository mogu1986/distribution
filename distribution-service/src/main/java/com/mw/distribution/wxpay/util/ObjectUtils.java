package com.mw.distribution.wxpay.util;



import com.mw.distribution.wxpay.exception.WeChatPayException;
import com.mw.distribution.wxpay.pojo.base.BasePayResponse;
import com.mw.distribution.wxpay.pojo.base.Coupon;
import com.mw.distribution.wxpay.pojo.base.ResponseStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * The type ObjectUtils.
 *
 */
public class ObjectUtils {
    private ObjectUtils() { }

    /**
     * 校验字符串不能为空.
     *
     * @param collection the collection
     * @param argumentName 参数名
     */
    public static void checkNotEmpty(final Collection<?> collection, final String argumentName) {
        if (null == collection || collection.isEmpty()) {
            throw new IllegalArgumentException("'" + argumentName + "' must be not empty");
        }
    }

    /**
     * 校验字符串不能为空.
     *
     * @param str the str
     * @param argumentName 参数名
     */
    public static void checkNotEmpty(final String str, final String argumentName) {
        if (null == str || str.isEmpty()) {
            throw new IllegalArgumentException("'" + argumentName + "' must be not empty");
        }
    }

    /**
     * 校验对象不能为 null.
     *
     * @param obj the obj
     * @param argumentName 参数名
     */
    public static void checkNotNull(final Object obj, final String argumentName) {
        if (null == obj) {
            throw new IllegalArgumentException("'" + argumentName + "' must be not null");
        }
    }

    /**
     * 从 otherParams 中取出相应的值转换为对应的枚举.
     *
     * @param <E> the Enum type parameter
     * @param key the key
     * @param clazz the Enum clazz
     * @param otherParams the other params
     *
     * @return the Enum
     */
    public static <E extends Enum<E>> E enumOf(
            final String key, final Class<E> clazz, final SortedMap<String, String> otherParams) {

        if (null != otherParams) {
            try {
                return Enum.valueOf(clazz, otherParams.get(key));
            }
            catch (final Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 转换枚举，忽略转换异常.
     *
     * @param value Enum value
     * @param clazz Enum class
     * @param <E> Enum Type
     *
     * @return Enum
     */
    public static <E extends Enum<E>> E enumOf(final String value, final Class<E> clazz) {
        if (value == null) {
            return null;
        }
        else {
            try {
                return Enum.valueOf(clazz, value);
            }
            catch (final Exception e) {
                return null;
            }
        }
    }

    /**
     * Gets all fields of the given class and its parents (if any) that are annotated with the given annotation.
     *
     * @param clazz the {@link Class} to query
     * @param annotationCls the {@link Annotation} that must be present on a field to be matched
     *
     * @return a list of Fields (possibly empty).
     *
     * @throws IllegalArgumentException if the class or annotation are {@code null}
     * @since 3.4
     */
    public static List<Field> getFieldsListWithAnnotation(final Class<?> clazz,
                                                          final Class<? extends Annotation> annotationCls) {
        checkNotNull(clazz, "The class must not be null");
        checkNotNull(annotationCls, "The annotation class must not be null");

        final List<Field> annotatedFields = new ArrayList<>();
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                if (field.getAnnotation(annotationCls) != null) {
                    AccessController.doPrivileged((PrivilegedAction<?>) () -> {
                        field.setAccessible(true);
                        return null;
                    });
                    annotatedFields.add(field);
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return annotatedFields;
    }

    /**
     * 生成长度为 32 的 uuid.
     *
     * @return uuid32 String
     */
    public static String uuid32() {
        final StringBuilder sb = new StringBuilder(UUID.randomUUID().toString());
        sb.deleteCharAt(8);
        sb.deleteCharAt(12);
        sb.deleteCharAt(16);
        sb.deleteCharAt(20);
        return sb.toString();
    }

    /**
     * 获取完整的 api url.
     *
     * @param basePath 微信 api basePath
     * @param apiPath 微信 api path
     *
     * @return 拼接后的 url.
     */
    public static String fullApiUrl(final String basePath, final String apiPath) {
        checkNotEmpty(basePath, "basePath");
        checkNotEmpty(apiPath, "apiPath");

        final String tmp1 = basePath.endsWith("/") ? basePath.substring(0, basePath.length() - 1) : basePath;
        final String tmp2 = apiPath.startsWith("/") ? (tmp1 + apiPath) : (tmp1 + "/" + apiPath);
        return tmp2.endsWith("/") ? tmp2.substring(0, tmp2.length() - 1) : tmp2;
    }

    /**
     * 校验响应信息是否为成功.
     *
     * @param response BasePayResponse
     * @param mchKey 商户秘钥
     *
     * @throws WeChatPayException 没有响应信息, 响应信息标示不成功时抛出此异常.
     */
    public static void checkSuccessful(final BasePayResponse response, final String mchKey) {
        if (response.getReturnCode() == ResponseStatus.FAIL) {
            throw new WeChatPayException("WeChat pay response 'return_msg' is '" + response.getReturnMsg() + "'");
        }
        if (!response.getSign().equals(SignUtils.generateSign(response, mchKey))) {
            throw new WeChatPayException("WeChat pay response 'sign' error");
        }
        if (!ResponseStatus.SUCCESS.equals(response.getResultCode())) {
            throw new WeChatPayException(
                    String.format("WeChat pay response 'err_code' is '%s', 'err_code_des' is '%s'",
                            response.getErrCode(),
                            response.getErrCodeDes()));
        }
    }

    /**
     * 判断响应信息是否为成功.
     *
     * @param response BasePayResponse
     * @param mchKey 商户秘钥
     *
     * @return 有响应信息, 并且完全成功返回 true
     */
    public static boolean isSuccessful(final BasePayResponse response, final String mchKey) {

        return response.getSign().equals(SignUtils.generateSign(response, mchKey))
                && (ResponseStatus.SUCCESS.equals(response.getReturnCode()))
                && (ResponseStatus.SUCCESS.equals(response.getResultCode()));
    }

    /**
     * 动态数据的映射转换.
     *
     * <p>针对如: coupon_id_$n, coupon_type_$n, coupon_fee_$n 等.
     *
     * <h3>样例:</h3>
     * <pre>
     * final Map&lt;String, BiConsumer&lt;String, Coupon&gt;&gt; mapping = new HashMap&lt;&gt;();
     * mapping.put("coupon_id_", (val, coupon) -&gt; coupon.setId(val));
     * mapping.put("coupon_type_", (val, coupon) -&gt; coupon.setType(val));
     * mapping.put("coupon_fee_", (val, coupon) -&gt; coupon.setFee(Integer.valueOf(val)));
     * ObjectUtils.beansMapFrom(this.otherParams, mapping, Coupon::new);
     * </pre>
     *
     * @param params 已存放的动态数据
     * @param mapping 转换函数的Map, 每一个 entry 的 key 为不带数字部分的前缀, 如 'coupon_id_'.
     *         value 为转换函数 BiConsumer&lt;V, T&gt; V 为 otherParams 的 value.
     * @param newT 新对象的创建函数
     * @param <T> 要转换的目标对象的类型
     *
     * @return 转换后的 Map, key 为 末尾数字, value 为转换后的对象.
     */
    public static <T> Map<String, T> beansMapFrom(
            final SortedMap<String, String> params,
            final Map<String, BiConsumer<String, T>> mapping,
            final Supplier<T> newT) {

        final Map<String, T> rtMap = new HashMap<>();
        for (final Map.Entry<String, String> entry : params.entrySet()) {

            final String key = entry.getKey();
            final String value = entry.getValue();
            if (null == value || value.isEmpty()) {
                continue;
            }
            for (final Map.Entry<String, BiConsumer<String, T>> mappingEntry : mapping.entrySet()) {
                final String keyStart = mappingEntry.getKey();
                if (key.matches(keyStart + "\\d+")) {
                    final String rtKey = key.substring(keyStart.length());
                    final T t = rtMap.computeIfAbsent(rtKey, k -> newT.get());
                    mappingEntry.getValue().accept(value, t);
                }
            }
        }

        return rtMap;
    }

    public static <T> List<T> beansFrom(
            final SortedMap<String, String> params,
            final Map<String, BiConsumer<String, T>> mapping,
            final Supplier<T> newT) {

        return new ArrayList<>(beansMapFrom(params, mapping, newT).values());
    }

    /**
     * 提取转换代金券信息.
     *
     * @param params params
     *
     * @return <code>Map&lt;String, Coupon&gt;</code>
     */
    public static List<Coupon> couponsFrom(final SortedMap<String, String> params) {
        final Map<String, BiConsumer<String, Coupon>> mappingMap = new HashMap<>(3);
        mappingMap.put("coupon_id_", (val, coupon) -> coupon.setId(val));
        mappingMap.put("coupon_type_", (val, coupon) -> coupon.setType(Coupon.Type.valueOf(val)));
        mappingMap.put("coupon_fee_", (val, coupon) -> coupon.setFee(Integer.valueOf(val)));

        return beansFrom(params, mappingMap, Coupon::new);
    }


}
