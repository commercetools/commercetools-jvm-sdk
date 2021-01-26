package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * Sets the key.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setKey()}
 */
public final class SetKey extends UpdateActionImpl<Cart> {
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
