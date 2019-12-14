package com.mw.distribution.wxpay.client;


import com.mw.distribution.wxpay.constant.WeChatPayURLConstant;
import com.mw.distribution.wxpay.pojo.*;
import com.mw.distribution.wxpay.pojo.base.BasePayResponse;
import com.mw.distribution.wxpay.util.CodecUtils;
import com.mw.distribution.wxpay.util.ObjectUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * 微信支付客户端-RestTemplate实现.
 *
 * @author  lumeng
 */
public class WeChatPayHttpComponentsClient implements WeChatPayClient {
    private static final String XML_TAG = "<xml>";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HttpClient httpClient;
    private final String basePath;

    /**
     * Instantiates a new WeChatPayHttpComponentsClient.
     *
     * @param apiBasePath apiBasePath
     * @param httpClient httpClient
     */
    public WeChatPayHttpComponentsClient(final String apiBasePath, final HttpClient httpClient) {

        this.basePath = apiBasePath;
        this.httpClient = httpClient;
    }

    /**
     * Instantiates a new WeChatPayHttpComponentsClient.
     *
     * @param httpClient httpClient
     */
    public WeChatPayHttpComponentsClient(final HttpClient httpClient) {

        this.basePath = WeChatPayURLConstant.BASE_PATH;
        this.httpClient = httpClient;
    }

    @Override
    public RefundQueryResponse refundQuery(final RefundQueryRequest request) {
        return doPost(WeChatPayURLConstant.REFUND_QUERY_PATH, request, RefundQueryResponse.class);
    }

    private <Q, S extends BasePayResponse> S doPost(final String apiPath, final Q request, final Class<S> responseClass) {

        final String responseStr;
        try {
            final HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(ObjectUtils.fullApiUrl(this.basePath, apiPath)));
            final String xmlReq = CodecUtils.marshal(request);
            this.log.info("WeChat pay request({}):\n{}", apiPath, xmlReq);
            httpPost.setEntity(new StringEntity(xmlReq, StandardCharsets.UTF_8));
            responseStr = EntityUtils.toString(this.httpClient.execute(httpPost).getEntity(), StandardCharsets.UTF_8);
            this.log.info("WeChat pay response({}):\n{}", apiPath, responseStr);
        }
        catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
        catch (final URISyntaxException e) {
            throw new IllegalArgumentException("Invalid 'url'", e);
        }
        return CodecUtils.unmarshal(responseStr, responseClass);
    }
}
