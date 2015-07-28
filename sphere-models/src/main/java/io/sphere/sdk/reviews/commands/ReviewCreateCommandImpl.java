package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;

/**
 Creates a review.

 {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandTest#execution()}

@see io.sphere.sdk.reviews.ReviewDraftBuilder
 */
public class ReviewCreateCommandImpl extends CreateCommandImpl<Review, ReviewDraft> {
    public ReviewCreateCommandImpl(final ReviewDraft body) {
        super(body, ReviewEndpoint.ENDPOINT);
    }

    public static ReviewCreateCommandImpl of(final ReviewDraft draft) {
        return new ReviewCreateCommandImpl(draft);
    }
}
