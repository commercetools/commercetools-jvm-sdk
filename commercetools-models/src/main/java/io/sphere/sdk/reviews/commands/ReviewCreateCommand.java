package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

/**
 * Creates a review.
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandIntegrationTest#createByCode()}
 *
 * @see io.sphere.sdk.reviews.ReviewDraftBuilder
 * @see io.sphere.sdk.reviews.ReviewDraft
 * @see io.sphere.sdk.reviews.Review
 */
public interface ReviewCreateCommand extends DraftBasedCreateCommand<Review, ReviewDraft>, MetaModelReferenceExpansionDsl<Review, ReviewCreateCommand, ReviewExpansionModel<Review>> {
    static ReviewCreateCommand of(final ReviewDraft draft) {
        return new ReviewCreateCommandImpl(draft);
    }
}
