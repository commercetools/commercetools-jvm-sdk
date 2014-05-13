package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public interface SphereRequestExecutor {
    <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch);
}