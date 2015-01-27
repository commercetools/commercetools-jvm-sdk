package io.sphere.sdk.meta;

import java.util.Locale;

import static org.junit.Assert.*;

public class KnownIssuesTest {
    public void localeWithCountry() {
        final Locale locale = Locale.US;//"en_US", not usable right now
        final Locale usableLocale = new Locale(locale.getLanguage());//"en" ok
    }

}