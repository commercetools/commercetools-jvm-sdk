package io.sphere.sdk.http;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

public interface ApacheHttpClientAdapter extends HttpClient {

    static HttpClient of(final CloseableHttpAsyncClient client) {
        return ApacheHttpClientAdapterImpl.of(client);
    }
}
