package io.sphere.sdk.reviews.commands.updateCommands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the text of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setText()}
 */
public class SetText extends UpdateAction<Review> {
    private final Optional<String> text;

    private SetText(final Optional<String> text) {
        super("setText");
        this.text = text;
    }

    public static SetText of(final Optional<String> text) {
        return new SetText(text);
    }

    public Optional<String> getText() {
        return text;
    }
}
