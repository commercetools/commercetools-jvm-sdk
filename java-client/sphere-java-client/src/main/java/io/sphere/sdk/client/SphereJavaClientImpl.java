package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;

public class SphereJavaClientImpl implements SphereJavaClient {
    private final SphereRequestExecutor sphereRequestExecutor;


    public SphereJavaClientImpl(final Config config) {
        this(config, new NingAsyncHttpClientHttpClient(config));
    }

    public SphereJavaClientImpl(final Config config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public SphereJavaClientImpl(final Config config, final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
    }

    @Override
    public <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch) {
        return sphereRequestExecutor.execute(fetch);
    }
}
