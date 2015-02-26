package io.sphere.sdk.client;

import io.sphere.sdk.http.*;
import io.sphere.sdk.models.Base;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class ApacheHttpClientAdapter extends Base {

    public static HttpClient of() {
        return of(HttpAsyncClients.createDefault());
    }

    public static HttpClient of(final CloseableHttpAsyncClient client) {
        return ApacheHttpClientAdapterImpl.of(client);
    }
}
