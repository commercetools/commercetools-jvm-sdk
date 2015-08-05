package io.sphere.sdk.test;

import io.sphere.sdk.models.LocalizedString;
import org.assertj.core.api.AbstractAssert;

import java.util.Locale;

public class LocalizedStringAssert extends AbstractAssert<LocalizedStringAssert, LocalizedString> {

    public LocalizedStringAssert(final LocalizedString localizedString) {
        super(localizedString, LocalizedStringAssert.class);
    }

    public static LocalizedStringAssert assertThat(final LocalizedString localizedString) {
        return new LocalizedStringAssert(localizedString);
    }

    public LocalizedStringAssert contains(final Locale locale, final String value) {
        if (!value.equals(actual.get(locale))) {
            failWithMessage(String.format("%s does not contain an entry %s -> %s", actual, locale, value));
        }
        return this;
    }

    public LocalizedStringAssert doesNotContain(final Locale locale, final String value) {
        if (value.equals(actual.get(locale))) {
            failWithMessage(String.format("%s does contain an entry %s -> %s", actual, locale, value));
        }
        return this;
    }
}
