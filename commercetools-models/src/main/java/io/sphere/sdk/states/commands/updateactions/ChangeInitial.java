package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.states.State;


/**
 * Configures if a state can be an initial state.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#changeInitial()}
 */
public final class ChangeInitial extends UpdateActionImpl<State> {
    private final boolean inital;

    private ChangeInitial(final boolean initial) {
        super("changeInitial");
        this.inital = initial;
    }

    public static ChangeInitial of(final boolean initial) {
        return new ChangeInitial(initial);
    }

    public boolean isInitial() {
        return inital;
    }
}
