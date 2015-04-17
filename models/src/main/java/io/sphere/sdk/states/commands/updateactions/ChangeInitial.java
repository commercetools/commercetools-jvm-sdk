package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.states.State;


/**
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandTest#changeInitial()}
 */
public class ChangeInitial extends UpdateAction<State> {
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
