package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslBuilder;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

import java.util.List;


final class ReviewUpdateCommandImpl extends UpdateCommandDslImpl<Review, ReviewUpdateCommand> implements ReviewUpdateCommand {
    ReviewUpdateCommandImpl(final Versioned<Review> versioned, final List<? extends UpdateAction<Review>> updateActions) {
        super(versioned, updateActions, ReviewEndpoint.ENDPOINT, ReviewUpdateCommandImpl::new);
    }

    ReviewUpdateCommandImpl(final UpdateCommandDslBuilder<Review, ReviewUpdateCommand> builder) {
        super(builder);
    }
}
