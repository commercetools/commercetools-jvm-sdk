package io.sphere.sdk.states.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Configures the allowed transitions to the next state.
 *Transitions are a way to describe possible transformations of the current state to other states of the same type (e.g.: Initial to Shipped). When performing a transitionState update action and transitions is set, the currently referenced state must have a transition to the new state.
 If transitions is an empty list, it means the current state is a final state and no further transitions are allowed.
 If transitions is not set, the validation is turned off. When performing a transitionState update action, any other state of the same type can be transitioned to.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandIntegrationTest#setTransitions()}
 */
public final class SetTransitions extends UpdateActionImpl<State> {
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
