package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

/**
 * Deletes a review in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewDeleteCommandTest#execution()}
 */
public class ReviewDeleteCommand extends ByIdDeleteCommandImpl<Review> {

    private ReviewDeleteCommand(final Versioned<Review> versioned) {
        super(versioned, ReviewEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Review> of(final Versioned<Review> versioned) {
        return new ReviewDeleteCommand(versioned);
    }
}
