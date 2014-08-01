package io.sphere.sdk.models;

import java.util.Locale;

public class LocalizedStringEntry extends Base {
    private final Locale locale;
    private final String value;

    public LocalizedStringEntry(final Locale locale, final String value) {
        this.locale = locale;
        this.value = value;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getValue() {
        return value;
    }
}
