package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

import java.util.List;

final class ReviewUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Review, ReviewUpdateCommand, ReviewExpansionModel<Review>> implements ReviewUpdateCommand {
    ReviewUpdateCommandImpl(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        super(versioned, updateActions, ReviewEndpoint.ENDPOINT, ReviewUpdateCommandImpl::new, ReviewExpansionModel.of());
    }

    ReviewUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Review, ReviewUpdateCommand, ReviewExpansionModel<Review>> builder) {
        super(builder);
    }
}
