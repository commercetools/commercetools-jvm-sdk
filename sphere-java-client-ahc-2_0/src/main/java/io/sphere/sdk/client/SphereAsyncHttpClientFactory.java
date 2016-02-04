package io.sphere.sdk.client;

import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

public final class SphereAsyncHttpClientFactory extends Base {
    private SphereAsyncHttpClientFactory() {
    }

    public static HttpClient create() {
        return AsyncHttpClientAdapter.of(new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"}).build()));
    }
}
