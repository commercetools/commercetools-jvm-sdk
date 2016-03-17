package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.relatedupdateactions.TransitionStateBase;

import javax.annotation.Nullable;

/**
 * Transition to a new state. If there is no state yet, the new state must be an initial state.
 * If the existing state has transitions set, there must be a direct transition to the new state.
 * If transitions is not set, no validation is performed.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#transitionState()}
 */
public final class TransitionState extends TransitionStateBase<Review> {
    private TransitionState(final @Nullable ResourceIdentifiable<State> state, @Nullable final Boolean force) {
        super(state, force);
    }

    public static TransitionState of(final @Nullable ResourceIdentifiable<State> state) {
        return new TransitionState(state, null);
    }

    public static TransitionState of(final @Nullable ResourceIdentifiable<State> state, @Nullable final Boolean force) {
        return new TransitionState(state, force);
    }
}
