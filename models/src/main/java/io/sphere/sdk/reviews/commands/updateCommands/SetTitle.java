package io.sphere.sdk.reviews.commands.updateCommands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the title of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setTitle()}
 */
public class SetTitle extends UpdateAction<Review> {
    private final Optional<String> title;

    private SetTitle(final Optional<String> title) {
        super("setTitle");
        this.title = title;
    }

    public static SetTitle of(final Optional<String> title) {
        return new SetTitle(title);
    }

    public Optional<String> getTitle() {
        return title;
    }
}
