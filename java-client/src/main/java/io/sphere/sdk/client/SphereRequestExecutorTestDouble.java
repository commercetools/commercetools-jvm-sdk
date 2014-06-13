package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

public abstract class SphereRequestExecutorTestDouble implements SphereRequestExecutor {
    @Override
    public final <I, R> ListenableFuture<Optional<I>> execute(final Fetch<I, R> fetch) {
        return Futures.immediateFuture(result(fetch));
    }

    protected <I, R> Optional<I> result(@SuppressWarnings("unused") final Fetch<I, R> fetch) {
        return Optional.absent();
    }

    @Override
    public final <I, R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I, R> query) {
        return Futures.immediateFuture(result(query));
    }

    protected <I, R> PagedQueryResult<I> result(@SuppressWarnings("unused") final Query<I, R> query) {
        return PagedQueryResult.empty();
    }

    @Override
    public void close() {
    }

    @Override
    public final <T, V> ListenableFuture<T> execute(final Command<T, V> command) {
        return Futures.immediateFuture(result(command));
    }

    protected <T, V> T result(@SuppressWarnings("unused") final Command<T, V> command) {
        throw new UnsupportedOperationException("override the " + SphereRequestExecutorTestDouble.class.getName() +
                ".result(final Command<T> command) method to return a value.");
    }
}
