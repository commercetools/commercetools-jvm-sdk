package io.sphere.sdk.selection;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

public final class StoreSelectionDsl extends Base implements StoreSelection {
    @Nullable
    private final String storeProjection;

    StoreSelectionDsl(@Nullable String storeProjection) {
        this.storeProjection = storeProjection;
    }

    public static StoreSelectionDsl of(final String storeSelection) {
        return StoreSelectionBuilder.of(storeSelection).build();
    }

    public StoreSelectionDsl withStoreProjection(@Nullable final String storeProjection) {
        return StoreSelectionBuilder.of(storeProjection).build();
    }

    @Nullable
    @Override
    public String getStoreProjection() {
        return storeProjection;
    }
}
