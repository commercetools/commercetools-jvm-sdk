package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class StoreSelectionBuilder extends Base implements Builder<StoreSelectionDsl> {
    @Nullable
    private String storeProjection;

    StoreSelectionBuilder(@Nullable final String storeProjection) {
        this.storeProjection = storeProjection;
    }

    public static StoreSelectionBuilder of(@Nullable final String storeProjection) {
        return new StoreSelectionBuilder(storeProjection);
    }

    @Nullable
    public String getStoreProjection() {
        return storeProjection;
    }

    @Override
    public StoreSelectionDsl build() {
        return new StoreSelectionDsl(storeProjection);
    }
}
