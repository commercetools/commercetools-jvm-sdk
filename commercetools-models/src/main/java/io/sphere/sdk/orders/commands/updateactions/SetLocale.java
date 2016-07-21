package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * Sets the locale.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#locale()}
 */
public final class SetLocale extends UpdateActionImpl<Order> {
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