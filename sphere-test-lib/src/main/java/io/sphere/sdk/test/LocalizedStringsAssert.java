package io.sphere.sdk.test;

import io.sphere.sdk.models.LocalizedStrings;
import org.assertj.core.api.AbstractAssert;

import java.util.Locale;

public class LocalizedStringsAssert extends AbstractAssert<LocalizedStringsAssert, LocalizedStrings> {

    public LocalizedStringsAssert(final LocalizedStrings localizedStrings) {
        super(localizedStrings, LocalizedStringsAssert.class);
    }

    public static LocalizedStringsAssert assertThat(final LocalizedStrings localizedStrings) {
        return new LocalizedStringsAssert(localizedStrings);
    }

    public LocalizedStringsAssert contains(final Locale locale, final String value) {
        if (!value.equals(actual.get(locale))) {
            failWithMessage(String.format("%s does not contain an entry %s -> %s", actual, locale, value));
        }
        return this;
    }

    public LocalizedStringsAssert doesNotContain(final Locale locale, final String value) {
        if (value.equals(actual.get(locale))) {
            failWithMessage(String.format("%s does contain an entry %s -> %s", actual, locale, value));
        }
        return this;
    }
}
