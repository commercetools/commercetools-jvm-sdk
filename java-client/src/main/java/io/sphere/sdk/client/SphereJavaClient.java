package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public interface SphereJavaClient {
    <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch);

    <I, R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I,R> query);

    void close();
}
