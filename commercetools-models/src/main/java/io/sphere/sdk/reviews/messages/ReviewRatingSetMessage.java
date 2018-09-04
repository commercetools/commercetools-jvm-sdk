package io.sphere.sdk.reviews.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.reviews.commands.updateactions.SetRating} update action.
 *
 * @see io.sphere.sdk.reviews.commands.updateactions.SetRating
 * @see Review#getRating()
 */
@JsonDeserialize(as = ReviewRatingSetMessage.class)//important to override annotation in Message class
public final class ReviewRatingSetMessage extends GenericMessageImpl<Review> {
    public static final String MESSAGE_TYPE = "ReviewRatingSet";
    public static final MessageDerivateHint<ReviewRatingSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ReviewRatingSetMessage.class, Review.referenceTypeId());

    @Nullable
    private final Integer oldRating;
    @Nullable
    private final Integer newRating;
    private final Boolean includedInStatistics;
    @Nullable
    private final Reference<JsonNode> target;

    @JsonCreator
    private ReviewRatingSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, @Nullable final Integer oldRating, @Nullable final Integer newRating, final Boolean includedInStatistics, @Nullable final Reference<JsonNode> target) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Review.class);
        this.oldRating = oldRating;
        this.newRating = newRating;
        this.includedInStatistics = includedInStatistics;
        this.target = target;
    }

    @Nullable
    public Integer getOldRating() {
        return oldRating;
    }

    @Nullable
    public Integer getNewRating() {
        return newRating;
    }

    public Boolean isIncludedInStatistics() {
        return includedInStatistics;
    }

    @Nullable
    public Reference<JsonNode> getTarget() {
        return target;
    }
}
