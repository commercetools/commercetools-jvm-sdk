package io.sphere.sdk.products.queries;

import io.sphere.sdk.stores.Store;

import javax.annotation.Nullable;

public interface StoreProjectionRequestDsl<T> {
    T withStoreProjection(@Nullable final Store storeProjection);

    @Nullable
    ProductProjectionQuery getStoreProjection();
}
