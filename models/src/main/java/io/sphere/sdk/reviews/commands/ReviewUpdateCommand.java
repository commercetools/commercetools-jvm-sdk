package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.commands.ReviewsEndpoint;
import io.sphere.sdk.commands.CreateCommandImpl;

import java.util.List;

import static java.util.Arrays.asList;

/** Creates a review.

 <p>Example:</p>
 {@include.example io.sphere.sdk.reviews.ReviewIntegrationTest#createReview()}

@see io.sphere.sdk.reviews.ReviewDraftBuilder
 */

public class ReviewUpdateCommand extends UpdateCommandDslImpl<Review> {
    private ReviewUpdateCommand(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        super(versioned, updateActions, ReviewsEndpoint.ENDPOINT);
    }

    public static ReviewUpdateCommand of(final Versioned<Review> versioned, final UpdateAction<Review> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static ReviewUpdateCommand of(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        return new ReviewUpdateCommand(versioned, updateActions);
    }
}
