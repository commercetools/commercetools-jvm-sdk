package io.sphere.sdk.http;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public interface ApacheHttpClientAdapter extends HttpClient {

    static HttpClient of() {
        return of(HttpAsyncClients.createDefault());
    }

    static HttpClient of(final CloseableHttpAsyncClient client) {
        return ApacheHttpClientAdapterImpl.of(client);
    }
}
