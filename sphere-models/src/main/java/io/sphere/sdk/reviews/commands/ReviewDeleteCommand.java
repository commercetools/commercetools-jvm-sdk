package io.sphere.sdk.reviews.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.reviews.Review;

/**
 * Deletes a review in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewDeleteCommandTest#execution()}
 */
public interface ReviewDeleteCommand extends ByIdDeleteCommand<Review> {

    static DeleteCommand<Review> of(final Versioned<Review> versioned) {
        return new ReviewDeleteCommandImpl(versioned);
    }
}
