package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewDraft;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

final class ReviewCreateCommandImpl extends MetaModelCreateCommandImpl<Review, ReviewCreateCommand, ReviewDraft, ReviewExpansionModel<Review>> implements ReviewCreateCommand {
    ReviewCreateCommandImpl(final MetaModelCreateCommandBuilder<Review, ReviewCreateCommand, ReviewDraft, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }

    ReviewCreateCommandImpl(final ReviewDraft body) {
        super(body, ReviewEndpoint.ENDPOINT, ReviewExpansionModel.of(), ReviewCreateCommandImpl::new);
    }
}
