package io.sphere.sdk.products.search;

import javax.annotation.Nullable;

public interface StoreSelectionRequestDsl<T> {
    T withStoreSelection(@Nullable final StoreSelection storeSelection);

    @Nullable
    StoreSelection getStoreSelection();
}
