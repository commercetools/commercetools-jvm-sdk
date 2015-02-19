package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletableFuture;

final class SphereClientImpl extends Base implements SphereClient {
    private final SphereRequestExecutor sphereRequestExecutor;


    private SphereClientImpl(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        this(config, NingAsyncHttpClientAdapter.of(), tokenSupplier);
    }

    private SphereClientImpl(final SphereApiConfig config, final HttpClient httpClient, final SphereAccessTokenSupplier tokenSupplier) {
        this(new HttpSphereRequestExecutor(httpClient, config, tokenSupplier));
    }

    private SphereClientImpl(final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
    }

    public <T> CompletableFuture<T> execute(final SphereRequest<T> requestable) {
        return sphereRequestExecutor.execute(requestable);
    }

    @Override
    public void close() {
        sphereRequestExecutor.close();
    }

    public static SphereClient of(final SphereApiConfig config, final SphereAccessTokenSupplier tokenSupplier) {
        return new SphereClientImpl(config, tokenSupplier);
    }

    public static SphereClient of(final SphereClientConfig config) {
        return new SphereClientImpl(config, SphereAccessTokenSupplier.ofAutoRefresh(config));
    }
}
