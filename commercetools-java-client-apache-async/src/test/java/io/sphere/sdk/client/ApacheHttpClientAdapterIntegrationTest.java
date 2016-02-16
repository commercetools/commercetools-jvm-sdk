package io.sphere.sdk.client;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class ApacheHttpClientAdapterIntegrationTest extends HttpClientAdapterTest {
    @Override
    protected HttpClient createClient() {
        return ApacheHttpClientAdapter.of(HttpAsyncClients.createDefault());
    }

    @Override
    protected int port() {
        return 5014;
    }
}
