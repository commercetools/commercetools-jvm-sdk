package io.sphere.sdk.reviews.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.reviews.commands.updateactions.TransitionState} update action.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#transitionState()}
 *
 * @see io.sphere.sdk.reviews.Review
 * @see Review#getState()
 * @see io.sphere.sdk.reviews.commands.updateactions.TransitionState
 */
@JsonDeserialize(as = ReviewStateTransitionMessage.class)//important to override annotation in Message class
public final class ReviewStateTransitionMessage  extends GenericMessageImpl<Review> {
    public static final String MESSAGE_TYPE = "ReviewStateTransition";
    public static final MessageDerivateHint<ReviewStateTransitionMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ReviewStateTransitionMessage.class, Review.referenceTypeId());

    @Nullable
    private final Reference<State> oldState;
    private final Reference<State> newState;
    @Nullable
    private final Reference<JsonNode> target;
    private final Boolean oldIncludedInStatistics;
    private final Boolean newIncludedInStatistics;

    @JsonCreator
    private ReviewStateTransitionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, @Nullable final Reference<State> oldState, final Reference<State> newState, @Nullable final Reference<JsonNode> target, final Boolean oldIncludedInStatistics, final Boolean newIncludedInStatistics) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Review.class);
        this.oldState = oldState;
        this.newState = newState;

        this.target = target;
        this.oldIncludedInStatistics = oldIncludedInStatistics;
        this.newIncludedInStatistics = newIncludedInStatistics;
    }

    @Nullable
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
