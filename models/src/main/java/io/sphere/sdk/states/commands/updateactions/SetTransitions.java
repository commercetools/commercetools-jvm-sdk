package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import java.util.List;

public class SetTransitions extends UpdateAction<State> {
    private final List<Reference<State>> transistions;

    private SetTransitions(final List<Reference<State>> transistions) {
        super("setTransitions");
        this.transistions = transistions;
    }

    public static SetTransitions of(final List<Reference<State>> transistions) {
        return new SetTransitions(transistions);
    }

    public List<Reference<State>> getTransistions() {
        return transistions;
    }
}
