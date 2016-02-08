package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

final class ReviewDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Review, ReviewDeleteCommand, ReviewExpansionModel<Review>> implements ReviewDeleteCommand {
    ReviewDeleteCommandImpl(final Versioned<Review> versioned) {
        super(versioned, ReviewEndpoint.ENDPOINT, ReviewExpansionModel.of(), ReviewDeleteCommandImpl::new);
    }


    ReviewDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Review, ReviewDeleteCommand, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }
}
