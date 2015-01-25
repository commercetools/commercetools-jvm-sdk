package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletableFuture;

final class SphereClientImpl extends Base implements SphereClient {
    private final SphereRequestExecutor sphereRequestExecutor;


    public SphereClientImpl(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        this(config, new NingAsyncHttpClient(config, tokenSupplier));
    }

    public SphereClientImpl(final SphereClientConfig config) {
        this(config, new NingAsyncHttpClient(config));
    }

    public SphereClientImpl(final SphereApiConfig config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public SphereClientImpl(final SphereApiConfig config, final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
    }

    public <T> CompletableFuture<T> execute(final SphereRequest<T> requestable) {
        return sphereRequestExecutor.execute(requestable);
    }

    @Override
    public void close() {
        sphereRequestExecutor.close();
    }
}
