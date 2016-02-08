package io.sphere.sdk.models;

import java.util.Locale;

public final class LocalizedStringEntry extends Base {
    private final Locale locale;
    private final String value;

    private LocalizedStringEntry(final Locale locale, final String value) {
        this.locale = locale;
        this.value = value;
    }

    public static LocalizedStringEntry of(final Locale locale, final String value) {
        return new LocalizedStringEntry(locale, value);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getValue() {
        return value;
    }
}
