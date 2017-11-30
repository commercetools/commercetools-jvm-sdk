package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

/**
 * Revert changes of a variant, which were made to the staged version of a product and reset to the
 +current version.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#revertStagedVariantChanges()}
 */
public final class RevertStagedVariantChanges extends UpdateActionImpl<Product> {

    private final Integer variantId;

    private RevertStagedVariantChanges(final Integer variantId) {
        super("revertStagedVariantChanges");
        this.variantId = variantId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public static RevertStagedVariantChanges of(final Integer variantId) {
        return new RevertStagedVariantChanges(variantId);
    }
}
