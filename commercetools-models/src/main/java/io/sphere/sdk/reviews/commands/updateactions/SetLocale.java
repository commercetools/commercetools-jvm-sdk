package io.sphere.sdk.reviews.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.reviews.Review;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Sets/unsets the locale of a review.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.reviews.commands.ReviewUpdateCommandIntegrationTest#setLocale()}
 */
public final class SetLocale extends UpdateActionImpl<Review> {
    @Nullable
    private final Locale locale;

    private SetLocale(@Nullable final Locale locale) {
        super("setLocale");
        this.locale = locale;
    }

    public static SetLocale of(@Nullable final Locale locale) {
        return new SetLocale(locale);
    }

    @Nullable
    public Locale getLocale() {
        return locale;
    }
}
