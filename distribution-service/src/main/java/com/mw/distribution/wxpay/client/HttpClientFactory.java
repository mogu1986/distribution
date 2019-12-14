package com.mw.distribution.wxpay.client;



import com.mw.distribution.wxpay.exception.UncheckedException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * HttpClient 创建工厂.
 */
public class HttpClientFactory {
    private final String key;
    private final String certificatePath;

    public HttpClientFactory(final String key, final String certificatePath) {
        this.key = key;
        this.certificatePath = certificatePath;
    }


    /**
     * Build HttpClient.
     *
     * @return the http client
     */
    public HttpClient build() {

        try (final FileInputStream stream = new FileInputStream(this.certificatePath)) {
            // 配置证书
            final KeyStore keystore = KeyStore.getInstance("PKCS12");
            final char[] keyArray = this.key.toCharArray();
            keystore.load(stream, keyArray);

            // ssl
            final SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keystore, keyArray).build();
            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());

            // httpclient
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        catch (final IOException | NoSuchAlgorithmException | CertificateException
                | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
            throw new UncheckedException("HttpClient build fail", e);
        }
    }
}
