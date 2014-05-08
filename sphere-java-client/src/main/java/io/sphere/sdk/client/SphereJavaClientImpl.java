package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;

public class SphereJavaClientImpl implements SphereJavaClient {
    private final Config config;
    private final RenameMe renameMe;


    public SphereJavaClientImpl(final Config config) {
        this(config, new NingAsyncHttpClientHttpRequestExecutor(config));
    }

    public SphereJavaClientImpl(final Config config, final HttpRequestExecutor requestExecutor) {
        this(config, new HttpRe(requestExecutor, config));
    }

    public SphereJavaClientImpl(final Config config, final RenameMe renameMe) {
        this.config = config;
        this.renameMe = renameMe;
    }

    @Override
    public <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch) {
        return renameMe.execute(fetch);
    }

    @Override
    public <T> ListenableFuture<PagedQueryResult<T>> execute(final Query<T> query) {
        return renameMe.execute(query);
    }
}
