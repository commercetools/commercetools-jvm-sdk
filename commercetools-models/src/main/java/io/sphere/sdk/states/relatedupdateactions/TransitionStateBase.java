package io.sphere.sdk.states.relatedupdateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Internal base class
 * @param <T> type to be updated
 */
public abstract class TransitionStateBase<T> extends UpdateActionImpl<T> {
    @Nullable
    private final ResourceIdentifier<State> state;

    protected TransitionStateBase(final @Nullable ResourceIdentifiable<State> state) {
        super("transitionState");
        this.state = Optional.ofNullable(state).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
    }

    @Nullable
    public ResourceIdentifier<State> getState() {
        return state;
    }
}