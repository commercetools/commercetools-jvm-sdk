package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import java.util.Optional;

/**
 * Updates the score of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setScore()}
 */
public class SetScore extends UpdateAction<Review> {
    private final Double score;

    private SetScore(final Optional<Double> score) {
        super("setScore");
        final boolean scoreIsInValidRange = score.map(scoreValue -> scoreValue >= 0.0 && scoreValue <= 1.0).orElse(true);
        if (!scoreIsInValidRange) {
            throw new IllegalArgumentException("Valid scores are in [0..1].");
        }
        this.score = score.orElse(null);
    }

    public static SetScore of(final double score) {
        return of(Optional.of(score));
    }

    public static SetScore of(final Optional<Double> score) {
        return new SetScore(score);
    }

    public Optional<Double> getScore() {
        return Optional.ofNullable(score);
    }
}
