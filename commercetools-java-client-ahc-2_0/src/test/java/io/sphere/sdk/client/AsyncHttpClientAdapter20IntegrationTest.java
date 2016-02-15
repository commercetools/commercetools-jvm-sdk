package io.sphere.sdk.client;

import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

public class AsyncHttpClientAdapter20IntegrationTest extends HttpClientAdapterTest {
    @Override
    protected HttpClient createClient() {
        return AsyncHttpClientAdapter.of(new DefaultAsyncHttpClient());
    }

    @Override
    protected int port() {
        return 5015;
    }
}
