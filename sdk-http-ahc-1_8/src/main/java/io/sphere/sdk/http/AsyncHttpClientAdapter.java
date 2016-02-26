package io.sphere.sdk.http;

import com.ning.http.client.AsyncHttpClient;

/**
 * Adapter to use {@code AsyncHttpClient} (version 1.8.x) as {@code HttpClient}.
 */
public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return new AsyncHttpClientAdapterImpl(asyncHttpClient);
    }
}