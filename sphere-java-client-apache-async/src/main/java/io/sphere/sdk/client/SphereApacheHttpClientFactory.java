package io.sphere.sdk.client;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public final class SphereApacheHttpClientFactory extends Base {
    private SphereApacheHttpClientFactory() {
    }

    public static HttpClient create() {
        return ApacheHttpClientAdapter.of(HttpAsyncClients.createDefault());
    }
}
