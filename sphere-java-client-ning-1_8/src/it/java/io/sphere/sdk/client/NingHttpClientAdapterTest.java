package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.NingHttpClientAdapter;

public class NingHttpClientAdapterTest extends HttpClientAdapterTest {
    @Override
    protected HttpClient createClient() {
        return NingHttpClientAdapter.of();
    }
}
