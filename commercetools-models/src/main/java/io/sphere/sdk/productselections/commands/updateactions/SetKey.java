package io.sphere.sdk.productselections.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.productselections.ProductSelection;

import javax.annotation.Nullable;

/**
 * Sets the key.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productselections.commands.ProductSelectionUpdateCommandIntegrationTest#setKey()}
 */
public final class SetKey extends UpdateActionImpl<ProductSelection> {
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
