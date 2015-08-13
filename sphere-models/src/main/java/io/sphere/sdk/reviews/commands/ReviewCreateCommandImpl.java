package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

/**
 Creates a review.

 {@include.example io.sphere.sdk.reviews.commands.ReviewCreateCommandTest#execution()}

@see io.sphere.sdk.reviews.ReviewDraftBuilder
 */
final class ReviewCreateCommandImpl extends MetaModelCreateCommandImpl<Review, ReviewCreateCommand, ReviewDraft, ReviewExpansionModel<Review>> implements ReviewCreateCommand {
    ReviewCreateCommandImpl(final MetaModelCreateCommandBuilder<Review, ReviewCreateCommand, ReviewDraft, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }

    public ReviewCreateCommandImpl(final ReviewDraft body) {
        super(body, ReviewEndpoint.ENDPOINT, ReviewExpansionModel.of(), ReviewCreateCommandImpl::new);
    }

    public static ReviewCreateCommandImpl of(final ReviewDraft draft) {
        return new ReviewCreateCommandImpl(draft);
    }
}
