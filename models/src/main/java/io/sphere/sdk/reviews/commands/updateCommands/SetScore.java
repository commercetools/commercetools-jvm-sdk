package io.sphere.sdk.reviews.commands.updateCommands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the score of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setScore()}
 */
public class SetScore extends UpdateAction<Review> {
    private final Optional<Float> score;

    private SetScore(final Optional<Float> score) {
        super("setScore");
        this.score = score;
    }

    public static SetScore of(final Optional<Float> score) {
        return new SetScore(score);
    }

    public Optional<Float> getScore() {
        return this.score;
    }
}
