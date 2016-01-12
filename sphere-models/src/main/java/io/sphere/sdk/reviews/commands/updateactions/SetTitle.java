package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

public class SetTitle extends UpdateActionImpl<Review> {
    @Nullable
    private final String title;

    private SetTitle(@Nullable final String title) {
        super("setText");
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
