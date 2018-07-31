package io.sphere.sdk.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Tls12Test {


    @Test
    public void test() throws Exception {


        SSLContext sslContext = SSLContexts.custom()
                .build();

        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1.2"},
                null,
                new DefaultHostnameVerifier());

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(factory).build();

        InputStream inputStream = httpClient.execute(new HttpGet("https://api.escemo.com")).getEntity().getContent();
        StringBuilder textBuilder = new StringBuilder();

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        System.out.println(textBuilder.toString());
    }


}
