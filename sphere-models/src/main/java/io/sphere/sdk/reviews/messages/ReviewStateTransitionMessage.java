package io.sphere.sdk.reviews.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**
 * This message is the result of the {@link io.sphere.sdk.reviews.commands.updateactions.TransitionState} update action.
 *
 * {@include.example }
 *
 * @see io.sphere.sdk.reviews.Review
 * @see Review#getState()
 * @see io.sphere.sdk.reviews.commands.updateactions.TransitionState
 */
public class ReviewStateTransitionMessage {
    public static final String MESSAGE_TYPE = "ReviewStateTransition";
    public static final MessageDerivateHint<ReviewStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ReviewStateTransitionMessage.class);


    private final Reference<State> oldState;
    private final Reference<State> newState;
    @Nullable
    private final Reference<JsonNode> target;
    private final Boolean oldIncludedInStatistics;
    private final Boolean newIncludedInStatistics;

    @JsonCreator
    private ReviewStateTransitionMessage(final Reference<State> oldState, final Reference<State> newState, final Reference<JsonNode> target, final Boolean oldIncludedInStatistics, final Boolean newIncludedInStatistics) {
        this.oldState = oldState;
        this.newState = newState;

        this.target = target;
        this.oldIncludedInStatistics = oldIncludedInStatistics;
        this.newIncludedInStatistics = newIncludedInStatistics;
    }

    public Reference<State> getOldState() {
        return oldState;
    }

    public Reference<State> getNewState() {
        return newState;
    }

    @Nullable
    public Reference<JsonNode> getTarget() {
        return target;
    }

    public Boolean getOldIncludedInStatistics() {
        return oldIncludedInStatistics;
    }

    public Boolean getNewIncludedInStatistics() {
        return newIncludedInStatistics;
    }
}
