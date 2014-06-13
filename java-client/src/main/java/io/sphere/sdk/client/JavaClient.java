package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

public interface JavaClient {
    <I, R> ListenableFuture<Optional<I>> execute(final Fetch<I, R> fetch);

    <I, R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I, R> query);

    <T, V> ListenableFuture<T> execute(final Command<T, V> command);

    void close();
}
