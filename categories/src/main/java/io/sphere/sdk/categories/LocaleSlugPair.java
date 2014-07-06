package io.sphere.sdk.categories;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Locale;

class LocaleSlugPair extends MutablePair<Locale, String> {
    private static final long serialVersionUID = 4954918890077093865L;

    public LocaleSlugPair(final Locale left, final String right) {
        super(left, right);
    }
}