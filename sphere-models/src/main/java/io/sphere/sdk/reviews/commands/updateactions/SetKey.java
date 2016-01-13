package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;

/**
 * Sets/unsets the key of a review
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandTest#setKey()}
 */
public class SetKey extends UpdateActionImpl<Review> {
    @Nullable
    private final String key;

    private SetKey(@Nullable final String key) {
        super("setKey");
        this.key = key;
    }

    public static SetKey of(@Nullable final String key) {
        return new SetKey(key);
    }

    @Nullable
    public String getKey() {
        return key;
    }
}
