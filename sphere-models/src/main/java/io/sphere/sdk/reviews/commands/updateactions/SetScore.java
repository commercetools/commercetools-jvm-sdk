package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Updates the score of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setScore()}
 */
public class SetScore extends UpdateActionImpl<Review> {
    @Nullable
    private final Double score;

    private SetScore(@Nullable final Double score) {
        super("setScore");
        final boolean scoreIsInValidRange = Optional.ofNullable(score).map(scoreValue -> scoreValue >= 0.0 && scoreValue <= 1.0).orElse(true);
        if (!scoreIsInValidRange) {
            throw new IllegalArgumentException("Valid scores are in [0..1].");
        }
        this.score = score;
    }

    public static SetScore of(@Nullable final Double score) {
        return new SetScore(score);
    }

    @Nullable
    public Double getScore() {
        return score;
    }
}
