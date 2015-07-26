package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

/**
 * Updates the title of a review.
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setTitle()}
 */
public class SetTitle extends UpdateAction<Review> {
    @Nullable
    private final String title;

    private SetTitle(@Nullable final String title) {
        super("setTitle");
        this.title = title;
    }

    public static SetTitle of(@Nullable final String title) {
        return new SetTitle(title);
    }

    @Nullable
    public String getTitle() {
        return title;
    }
}
