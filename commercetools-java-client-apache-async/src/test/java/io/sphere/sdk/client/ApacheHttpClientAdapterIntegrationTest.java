package io.sphere.sdk.client;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

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
