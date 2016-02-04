package io.sphere.sdk.http;

import org.asynchttpclient.AsyncHttpClient;

/**
 * Adapter to use {@code AsyncHttpClient} (version 1.0.x) as {@code HttpClient}.
 */
public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return DefaultAsyncHttpClientAdapterImpl.of(asyncHttpClient);
    }
}