package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

final class ReviewDeleteCommandImpl extends ByIdDeleteCommandImpl<Review> {

    ReviewDeleteCommandImpl(final Versioned<Review> versioned) {
        super(versioned, ReviewEndpoint.ENDPOINT);
    }
}
