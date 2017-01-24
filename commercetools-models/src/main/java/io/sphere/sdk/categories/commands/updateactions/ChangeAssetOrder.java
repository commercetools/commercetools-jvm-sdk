package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;

import java.util.List;

/**
 * Changes the order of the assets list. The new order is defined by listing the ids of the assets.
 */
public final class ChangeAssetOrder extends UpdateActionImpl<Category> {
    private final List<String> assetOrder;

    private ChangeAssetOrder(final List<String> assetOrder) {
        super("changeAssetOrder");
        this.assetOrder = assetOrder;
    }

    public List<String> getAssetOrder() {
        return assetOrder;
    }

    public static ChangeAssetOrder of(final List<String> assetOrder) {
        return new ChangeAssetOrder(assetOrder);
    }
}
