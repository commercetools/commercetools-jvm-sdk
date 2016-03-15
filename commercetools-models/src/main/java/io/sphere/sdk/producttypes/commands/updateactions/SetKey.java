package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

import javax.annotation.Nullable;

/**
 * Sets/unsets the key of a {@link io.sphere.sdk.producttypes.ProductType}
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#setKey()}
 */
public final class SetKey extends UpdateActionImpl<ProductType> {
    @Nullable
    private final String key;

    private SetKey(@Nullable final String key) {
        super("setKey");
        this.key = key;
    }

    public static SetKey of(@Nullable final String key) {
        return new SetKey(key);
    }

    @Nullable
    public String getKey() {
        return key;
    }
}
