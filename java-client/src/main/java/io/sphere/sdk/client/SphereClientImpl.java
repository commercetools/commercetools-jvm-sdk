package io.sphere.sdk.client;

import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpClient;

import java.util.concurrent.CompletableFuture;

final class SphereClientImpl implements SphereClient {
    private final SphereRequestExecutor sphereRequestExecutor;
    private final SphereClientConfig config;


    public SphereClientImpl(final SphereClientConfig config) {
        this(config, new NingAsyncHttpClient(config));

    }

    public SphereClientImpl(final SphereClientConfig config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public SphereClientImpl(final SphereClientConfig config, final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
        this.config = config;
    }

    public <T> CompletableFuture<T> execute(final ClientRequest<T> requestable) {
        return sphereRequestExecutor.execute(requestable);
    }

    @Override
    public void close() {
        sphereRequestExecutor.close();
    }
}
