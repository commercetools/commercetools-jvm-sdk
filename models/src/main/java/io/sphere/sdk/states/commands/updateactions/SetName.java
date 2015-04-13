package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.State;

public class SetName extends UpdateAction<State> {
    private final LocalizedStrings name;

    private SetName(LocalizedStrings name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(final LocalizedStrings name) {
        return new SetName(name);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
