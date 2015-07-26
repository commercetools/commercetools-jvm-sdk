package io.sphere.sdk.states.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Set;

import static io.sphere.sdk.utils.SetUtils.asSet;

/**
 * {@include.example io.sphere.sdk.states.commands.StateUpdateCommandTest#setTransitions()}
 */
public class SetTransitions extends UpdateAction<State> {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final Set<Reference<State>> transitions;

    private SetTransitions(final Set<Reference<State>> transitions) {
        super("setTransitions");
        this.transitions = transitions;
    }

    public static SetTransitions of() {
        return of((Set<Reference<State>>)null);
    }

    public static SetTransitions of(@Nullable final Set<Reference<State>> transitions) {
        return new SetTransitions(transitions);
    }

    public static SetTransitions of(final Referenceable<State> state) {
        return of(asSet(state.toReference()));
    }

    @Nullable
    public Set<Reference<State>> getTransitions() {
        return transitions;
    }
}
