package io.sphere.sdk.http;

import com.ning.http.client.*;

/**
 * Adapter to use {@link AsyncHttpClient} version 1.9.x as {@link HttpClient}.
 */
public interface AsyncHttpClientAdapter extends HttpClient {

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return DefaultAsyncHttpClientAdapterImpl.of(asyncHttpClient);
    }
}