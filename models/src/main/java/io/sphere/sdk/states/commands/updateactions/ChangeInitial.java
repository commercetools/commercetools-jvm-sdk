package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.states.State;

public class ChangeInitial extends UpdateAction<State> {
    private final Boolean inital;

    private ChangeInitial(final Boolean inital) {
        super("changeInitial");
        this.inital = inital;
    }

    public ChangeInitial of(final Boolean inital) {
        return new ChangeInitial(inital);
    }

    public Boolean getInital() {
        return inital;
    }
}
