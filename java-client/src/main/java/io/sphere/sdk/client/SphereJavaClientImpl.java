package io.sphere.sdk.client;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;
import io.sphere.sdk.utils.IterableUtils;

public class SphereJavaClientImpl implements SphereJavaClient {
    private final SphereRequestExecutor sphereRequestExecutor;


    public SphereJavaClientImpl(final Config config) {
        this(config, new NingAsyncHttpClient(config));
    }

    public SphereJavaClientImpl(final Config config, final HttpClient httpClient) {
        this(config, new HttpSphereRequestExecutor(httpClient, config));
    }

    public SphereJavaClientImpl(final Config config, final SphereRequestExecutor sphereRequestExecutor) {
        this.sphereRequestExecutor = sphereRequestExecutor;
    }

    @Override
    public <I, R> ListenableFuture<Optional<I>> execute(final Fetch<I, R> fetch) {
        return sphereRequestExecutor.execute(fetch);
    }

    @Override
    public <I, R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I, R> query) {
        return sphereRequestExecutor.execute(query);
    }

    @Override
    public <I, R> ListenableFuture<I> execute(Command<I, R> command) {
        return sphereRequestExecutor.execute(command);
    }

    @Override
    public void close() {
        sphereRequestExecutor.close();
    }
}
