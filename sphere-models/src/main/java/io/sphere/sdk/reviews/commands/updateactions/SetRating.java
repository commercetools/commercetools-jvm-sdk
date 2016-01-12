package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

public class SetRating extends UpdateActionImpl<Review> {
    @Nullable
    private final Double rating;

    private SetRating(@Nullable final Double rating) {
        super("setRating");
        this.rating = rating;
    }

    public static SetRating of(@Nullable final Double rating) {
        return new SetRating(rating);
    }

    @Nullable
    public Double getRating() {
        return rating;
    }
}
