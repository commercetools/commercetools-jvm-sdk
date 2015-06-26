package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.NingHttpClientAdapter;

final class NingHttpClientAdapterFactory {

    public static HttpClient create() {
        return NingHttpClientAdapter.of(new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"}).build()));
    }
}
