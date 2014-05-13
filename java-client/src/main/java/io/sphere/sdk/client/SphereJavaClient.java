package io.sphere.sdk.client;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;

public interface SphereJavaClient {
    public <T> ListenableFuture<Optional<T>> execute(final Fetch<T> fetch);
}
