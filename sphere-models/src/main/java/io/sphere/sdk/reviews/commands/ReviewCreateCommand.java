package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

/**
 Creates a review.

 {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandTest#execution()}

@see io.sphere.sdk.reviews.ReviewDraftBuilder
 */
public interface ReviewCreateCommand extends CreateCommand<Review>, MetaModelExpansionDsl<Review, ReviewCreateCommand, ReviewExpansionModel<Review>> {
    static ReviewCreateCommand of(final ReviewDraft draft) {
        return new ReviewCreateCommandImpl(draft);
    }
}
