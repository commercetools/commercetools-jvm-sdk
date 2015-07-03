package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;

public class AsyncHttpClientAdapterTest extends HttpClientAdapterTest {
    @Override
    protected HttpClient createClient() {
        return AsyncHttpClientAdapter.of();
    }
}
