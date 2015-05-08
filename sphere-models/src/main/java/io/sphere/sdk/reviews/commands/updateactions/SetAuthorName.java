package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the author name of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setAuthorName()}
 */
public class SetAuthorName extends UpdateAction<Review> {
    private final String authorName;

    private SetAuthorName(final Optional<String> authorName) {
        super("setAuthorName");
        this.authorName = authorName.orElse(null);
    }

    public static SetAuthorName of(final String authorName) {
        return of(Optional.of(authorName));
    }

    public static SetAuthorName of(final Optional<String> authorName) {
        return new SetAuthorName(authorName);
    }

    public Optional<String> getAuthorName() {
        return Optional.ofNullable(authorName);
    }
}
