package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.Type;

/**
 *
 * Changes the key of a {@link Type}.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#changeKey()}
 */
public final class ChangeKey extends UpdateActionImpl<Type> {
    private final String key;

    private ChangeKey(final String key) {
        super("changeKey");
        this.key = key;
    }

    public static ChangeKey of(final String key) {
        return new ChangeKey(key);
    }

    public String getKey() {
        return key;
    }
}
