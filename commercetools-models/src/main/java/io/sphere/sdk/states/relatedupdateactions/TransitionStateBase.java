package io.sphere.sdk.states.relatedupdateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Nullable
    private final Boolean force;

    protected TransitionStateBase(final @Nullable ResourceIdentifiable<State> state, final Boolean force) {
        super("transitionState");
        this.force = force;
        this.state = Optional.ofNullable(state).map(ResourceIdentifiable::toResourceIdentifier).orElse(null);
    }

    protected TransitionStateBase(final @Nullable ResourceIdentifiable<State> state) {
        this(state, null);
    }

    @Nullable
    public ResourceIdentifier<State> getState() {
        return state;
    }

    /**
     * Using true disables the state machine validation and allows a transition in any state.
     *
     * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#transitionStateAndForce()}
     *
     * @return true if validation is turned of
     */
    @JsonProperty("force")
    @Nullable
    public Boolean isForce() {
        return force;
    }
}