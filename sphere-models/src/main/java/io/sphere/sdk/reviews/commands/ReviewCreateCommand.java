package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

/**
 * Creates a review.
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandTest#createByCode()}
 *
 * @see io.sphere.sdk.reviews.ReviewDraftBuilder
 * @see io.sphere.sdk.reviews.ReviewDraft
 * @see io.sphere.sdk.reviews.Review
 */
public interface ReviewCreateCommand extends CreateCommand<Review>, MetaModelReferenceExpansionDsl<Review, ReviewCreateCommand, ReviewExpansionModel<Review>> {
    static ReviewCreateCommand of(final ReviewDraft draft) {
        return new ReviewCreateCommandImpl(draft);
    }
}
