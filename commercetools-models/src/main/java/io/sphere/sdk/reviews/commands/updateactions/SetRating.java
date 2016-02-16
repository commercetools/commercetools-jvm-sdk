package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

/**
 * Sets/unsets the rating of a review.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#setRating()}
 */
public final class SetRating extends UpdateActionImpl<Review> {
    @Nullable
    private final Integer rating;

    private SetRating(@Nullable final Integer rating) {
        super("setRating");
        this.rating = rating;
    }

    public static SetRating of(@Nullable final Integer rating) {
        return new SetRating(rating);
    }

    @Nullable
    public Integer getRating() {
        return rating;
    }
}
