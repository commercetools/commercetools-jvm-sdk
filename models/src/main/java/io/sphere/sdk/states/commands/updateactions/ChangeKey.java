package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.states.State;

public class ChangeKey extends UpdateAction<State> {
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
