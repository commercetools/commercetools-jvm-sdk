package io.sphere.sdk.models;

import java.util.Locale;

public class LocalizedStringsEntry extends Base {
    private final Locale locale;
    private final String value;

    private LocalizedStringsEntry(final Locale locale, final String value) {
        this.locale = locale;
        this.value = value;
    }

    public static LocalizedStringsEntry of(final Locale locale, final String value) {
        return new LocalizedStringsEntry(locale, value);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getValue() {
        return value;
    }
}
