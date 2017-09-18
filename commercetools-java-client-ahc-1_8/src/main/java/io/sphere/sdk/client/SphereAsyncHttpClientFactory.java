package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;

public final class SphereAsyncHttpClientFactory extends SphereHttpClientFactory{

    @Deprecated
    public static HttpClient create() {
        return AsyncHttpClientAdapter.of(new AsyncHttpClient());
    }

    @Override
    public HttpClient getClient() {
        return create();
    }
}


