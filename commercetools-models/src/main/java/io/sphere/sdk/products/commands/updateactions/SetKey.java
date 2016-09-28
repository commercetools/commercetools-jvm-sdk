package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;

/**
 * Changes the key of a {@link Product}, for {@link io.sphere.sdk.products.ProductVariant}s use {@link SetProductVariantKey}.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setKey()}
 *
 * @see Product#getKey()
 * @see ProductProjection#getKey()
 */
public final class SetKey extends UpdateActionImpl<Product> {
    @Nullable
    private final String key;

    private SetKey(@Nullable final String key) {
        super("setKey");
        this.key = key;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    public static SetKey of(@Nullable final String key) {
        return new SetKey(key);
    }
}
