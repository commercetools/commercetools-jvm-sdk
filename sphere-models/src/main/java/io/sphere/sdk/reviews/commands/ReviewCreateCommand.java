package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;

/**
 Creates a review.

 {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandTest#execution()}

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
