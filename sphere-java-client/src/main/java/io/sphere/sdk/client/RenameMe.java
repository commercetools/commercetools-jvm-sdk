package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public interface RenameMe {
    <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch);

    <T> ListenableFuture<PagedQueryResult<T>> execute(Query<T> query);
}