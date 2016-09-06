package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Changes the key of a {@link io.sphere.sdk.products.ProductVariant}, for {@link Product}s use {@link SetKey}.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setProductVariantKey()}
 *
 */
public final class SetProductVariantKey extends UpdateActionImpl<Product> {
    @Nullable
    private final String key;
    @Nullable
    private final Integer variantId;

    private SetProductVariantKey(@Nullable final String key, @Nullable final Integer variantId) {
        super("setProductVariantKey");
        this.key = key;
        this.variantId = variantId;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    public static SetProductVariantKey ofKeyAndVariantId(@Nullable final String key, final Integer variantId) {
        return new SetProductVariantKey(key, variantId);
    }
}
