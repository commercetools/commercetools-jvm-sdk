package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the text of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setText()}
 */
public class SetText extends UpdateAction<Review> {
    private final String text;

    private SetText(final Optional<String> text) {
        super("setText");
        this.text = text.orElse(null);
    }

    public static SetText of(final String text) {
        return of(Optional.of(text));
    }

    public static SetText of(final Optional<String> text) {
        return new SetText(text);
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }
}
