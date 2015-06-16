package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.sdk.http.HttpClient;

public interface NingHttpClientAdapter extends HttpClient {

    static HttpClient of() {
        return of(new AsyncHttpClient());
    }

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return NingHttpClientAdapterImpl.of(asyncHttpClient);
    }
}