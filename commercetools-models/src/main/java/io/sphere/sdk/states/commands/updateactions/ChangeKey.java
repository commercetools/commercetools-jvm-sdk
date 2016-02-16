package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.states.State;

/**
 *
 * Changes the key of a state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#changeKey()}
 */
public final class ChangeKey extends UpdateActionImpl<State> {
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
