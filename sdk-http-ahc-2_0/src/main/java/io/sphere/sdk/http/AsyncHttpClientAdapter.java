package io.sphere.sdk.http;

import org.asynchttpclient.AsyncHttpClient;

/**
 * Adapter to use {@code AsyncHttpClient} (version 2.0.x) as {@code HttpClient}.
 */
public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return new DefaultAsyncHttpClient2_0AdapterImpl(asyncHttpClient);
    }
}