package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;

/**
 * Changes the key of a {@link io.sphere.sdk.categories.Category}.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setKey()}
 *
 * @see Category#getKey()
 */
public final class SetKey extends UpdateActionImpl<Category> {
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

    public static SetKey ofUnset() {
        return new SetKey(null);
    }

}