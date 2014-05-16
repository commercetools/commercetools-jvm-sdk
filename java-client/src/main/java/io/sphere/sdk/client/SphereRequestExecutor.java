package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public interface SphereRequestExecutor {
    <I, R> ListenableFuture<Optional<I>> execute(final Fetch<I, R> fetch);

    void close();

    <I, R> ListenableFuture<PagedQueryResult<I>> execute(Query<I, R> query);

    <I, R> ListenableFuture<I> execute(Command<I, R> command);
}