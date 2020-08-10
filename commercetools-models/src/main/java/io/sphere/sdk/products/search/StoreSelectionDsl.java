package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

public final class StoreSelectionDsl extends Base implements StoreSelection {
    @Nullable
    private final String storeProjection;

    StoreSelectionDsl(@Nullable String storeProjection) {
        this.storeProjection = storeProjection;
    }

    public StoreSelectionDsl withStoreProjection(@Nullable final String storeProjection) {
        return StoreSelectionBuilder.of(storeProjection).build();
    }

    @Nullable
    @Override
    public String getStoreProjection() {
        return null;
    }
}
