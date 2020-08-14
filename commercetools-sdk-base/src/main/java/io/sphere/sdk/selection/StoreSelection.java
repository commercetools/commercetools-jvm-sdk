package io.sphere.sdk.selection;

import javax.annotation.Nullable;

/**
 * Parameters to select prices in {@link ProductProjectionSearch}.
 *
 * Use {@link StoreSelectionBuilder} or {@link StoreSelectionDsl} to create an instance.
 *
 *
 */
public interface StoreSelection {
    @Nullable
    String getStoreProjection();

    static StoreSelectionDsl of(final String storeProjection)
    {
        return StoreSelectionBuilder.of(storeProjection).build();
    }
}
