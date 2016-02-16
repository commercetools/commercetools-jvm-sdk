package io.sphere.sdk.client;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.AsyncHttpClientAdapter;

public class AsyncHttpClientAdapter19IntegrationTest extends HttpClientAdapterTest {
    @Override
    protected HttpClient createClient() {
        return AsyncHttpClientAdapter.of(new AsyncHttpClient());
    }

    @Override
    protected int port() {
        return 5012;
    }
}
