package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class SphereRequestExecutorTestDouble implements SphereRequestExecutor {
    @Override
    public final <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch) {
        return Futures.immediateFuture(result(fetch));
    }

    public <T> Optional<T> result(final Fetch<T> fetch) {
        return Optional.absent();
    }

    @Override
    public final <I,R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I,R> query) {
        return Futures.immediateFuture(result(query));
    }

    public <I,R> PagedQueryResult<I> result(final Query<I,R> query) {
        return PagedQueryResult.empty();
    }

    @Override
    public void close() {
    }
}
