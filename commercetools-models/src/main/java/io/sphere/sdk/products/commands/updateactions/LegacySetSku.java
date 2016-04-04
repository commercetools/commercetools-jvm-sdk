package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds, changes or removes an SKU on a product variant.
 * An SKU can only be changed or removed from a variant through this operation
 * if there is no inventory entry associated with that SKU.
 * This change can never be staged and is thus immediately visible in published products.
 *
 * {@doc.gen intro}
 *
 * @deprecated use {@link SetSku} instead which updates the SKU just in staged
 */
@Deprecated//do not delete soon
public final class LegacySetSku extends UpdateActionImpl<Product> {
    private final Integer variantId;
    @Nullable
    private final String sku;

    private LegacySetSku(final Integer variantId, @Nullable final String sku) {
        super("setSKU" /*sic! */);
        this.variantId = variantId;
        this.sku = sku;
    }

    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static LegacySetSku of(final Integer variantId, @Nullable final String sku) {
        return new LegacySetSku(variantId, sku);
    }
}
