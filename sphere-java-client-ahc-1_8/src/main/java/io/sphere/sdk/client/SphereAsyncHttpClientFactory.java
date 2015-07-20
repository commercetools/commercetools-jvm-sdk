package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;

public final class SphereAsyncHttpClientFactory {
    private SphereAsyncHttpClientFactory() {
    }

    public static HttpClient create() {
        return AsyncHttpClientAdapter.of(new AsyncHttpClient());
    }
}


