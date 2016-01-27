package io.sphere.sdk.http;

import com.ning.http.client.AsyncHttpClient;

/**
 * Adapter to use {@link AsyncHttpClient} (version 1.8.x) as {@link HttpClient}.
 */
public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return AsyncHttpClientAdapterImpl.of(asyncHttpClient);
    }
}