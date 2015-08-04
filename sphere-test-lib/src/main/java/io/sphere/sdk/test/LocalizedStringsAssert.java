package io.sphere.sdk.test;

import io.sphere.sdk.models.LocalizedString;
import org.assertj.core.api.AbstractAssert;

import java.util.Locale;

public class LocalizedStringsAssert extends AbstractAssert<LocalizedStringsAssert, LocalizedString> {

    public LocalizedStringsAssert(final LocalizedString localizedString) {
        super(localizedString, LocalizedStringsAssert.class);
    }

    public static LocalizedStringsAssert assertThat(final LocalizedString localizedString) {
        return new LocalizedStringsAssert(localizedString);
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
