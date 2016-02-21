package io.sphere.sdk.models;

import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

public class LocalizedStringEntryTest {

    @Test
    public void ofLanguageTagAndValueForLanguage() {
        final LocalizedStringEntry entry = LocalizedStringEntry.of("de", "foo");
        assertThat(entry.getLocale()).isEqualTo(Locale.GERMAN);
        assertThat(entry.getValue()).isEqualTo("foo");
    }

    @Test
    public void ofLanguageTagAndValueForLanguageTag() {
        final LocalizedStringEntry entry = LocalizedStringEntry.of("de-DE", "foo");
        assertThat(entry.getLocale()).isEqualTo(Locale.GERMANY);
        assertThat(entry.getValue()).isEqualTo("foo");
    }
}