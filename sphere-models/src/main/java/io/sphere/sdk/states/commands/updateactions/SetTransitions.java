package io.sphere.sdk.states.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandTest#setTransitions()}
 */
public class SetTransitions extends UpdateActionImpl<State> {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Set<Reference<State>> transitions;

    private SetTransitions(@Nullable final Set<Reference<State>> transitions) {
        super("setTransitions");
        this.transitions = transitions;
    }

    public static SetTransitions of(@Nullable final Set<Reference<State>> transitions) {
        return new SetTransitions(transitions);
    }

    @Nullable
    public Set<Reference<State>> getTransitions() {
        return transitions;
    }
}
