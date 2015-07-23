package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandTest#setName()}
 */
public class SetName extends UpdateAction<State> {
    @Nullable
    private final LocalizedStrings name;

    private SetName(@Nullable final LocalizedStrings name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(@Nullable final LocalizedStrings name) {
        return new SetName(name);
    }

    @Nullable
    public LocalizedStrings getName() {
        return name;
    }
}