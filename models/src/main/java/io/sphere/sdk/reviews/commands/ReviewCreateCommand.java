package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/** Creates a review.

 <p>Example:</p>
 {@include.example io.sphere.sdk.reviews.ReviewIntegrationTest#createReview()}

@see io.sphere.sdk.reviews.ReviewDraftBuilder
 */

public class ReviewCreateCommand extends CreateCommandImpl<Review, ReviewDraft> {
    public ReviewCreateCommand(final ReviewDraft body) {
        super(body, ReviewsEndpoint.ENDPOINT);
    }

    public static ReviewCreateCommand of(final ReviewDraft draft) {
        return new ReviewCreateCommand(draft);
    }
}
