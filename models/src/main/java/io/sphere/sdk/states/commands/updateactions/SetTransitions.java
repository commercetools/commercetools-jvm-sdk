package io.sphere.sdk.states.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import java.util.Optional;
import java.util.Set;

/**
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandTest#setTransitions()}
 */
public class SetTransitions extends UpdateAction<State> {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Optional<Set<Reference<State>>> transitions;

    private SetTransitions(final Optional<Set<Reference<State>>> transitions) {
        super("setTransitions");
        this.transitions = transitions;
    }

    public static SetTransitions of(final Set<Reference<State>> transitions) {
        return of(Optional.of(transitions));
    }
    
    public static SetTransitions of(final Optional<Set<Reference<State>>> transitions) {
        return new SetTransitions(transitions);
    }

    public Optional<Set<Reference<State>>> getTransitions() {
        return transitions;
    }
}
