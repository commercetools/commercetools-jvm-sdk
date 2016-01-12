package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.relatedupdateactions.TransitionStateBase;

import javax.annotation.Nullable;

public class TransitionState extends TransitionStateBase<Review> {
    private TransitionState(final @Nullable Referenceable<State> state) {
        super(state);
    }

    public static TransitionState of(final @Nullable Referenceable<State> state) {
        return new TransitionState(state);
    }
}
