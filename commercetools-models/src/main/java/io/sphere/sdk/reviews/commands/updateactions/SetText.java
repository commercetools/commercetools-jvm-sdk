package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

/**
 * Sets/unsets the content of a review.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#setText()}
 */
public final class SetText extends UpdateActionImpl<Review> {
    @Nullable
    private final String text;

    private SetText(@Nullable final String text) {
        super("setText");
        this.text = text;
    }

    public static SetText of(@Nullable final String text) {
        return new SetText(text);
    }

    @Nullable
    public String getText() {
        return text;
    }
}
