package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**
 * Sets/unsets the name of a state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#setName()}
 */
public final class SetName extends UpdateActionImpl<State> {
    @Nullable
    private final LocalizedString name;

    private SetName(@Nullable final LocalizedString name) {
        super("setName");
        this.name = name;
    }

    public static SetName of(@Nullable final LocalizedString name) {
        return new SetName(name);
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }
}