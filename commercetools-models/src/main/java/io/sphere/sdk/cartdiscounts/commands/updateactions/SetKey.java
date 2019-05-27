package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * Changes the key of a {@link CartDiscount}.
 *
 * {@doc.gen intro}
 *
 * @see CartDiscount#getKey()
 */
public final class SetKey extends UpdateActionImpl<CartDiscount> {
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
