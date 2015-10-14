package io.sphere.sdk.states.relatedupdateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Internal base class
 * @param <T> type to be updated
 */
public abstract class TransitionStateBase<T> extends UpdateActionImpl<T> {
    @Nullable
    private final Reference<State> state;

    protected TransitionStateBase(final @Nullable Referenceable<State> state) {
        super("transitionState");
        this.state = Optional.ofNullable(state).map(stateReferenceable -> stateReferenceable.toReference()).orElse(null);
    }

    @Nullable
    public Reference<State> getState() {
        return state;
    }
}