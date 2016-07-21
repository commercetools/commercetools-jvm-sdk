package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Sets the locale.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#locale()}
 */
public final class SetLocale extends UpdateActionImpl<Cart> {
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