package io.sphere.sdk.commontypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import net.jcip.annotations.Immutable;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A wrapper around an attribute which can be translated into a number of locales.
 * Note that even if your project only uses one language some attributes (name and description for example) will be
 * always be LocalizableStrings.
 */
@Immutable
public class LocalizedString {

    private final Map<Locale, String> translations;

    @JsonCreator
    public LocalizedString(final Map<Locale, String> translations) {
        this.translations = ImmutableMap.copyOf(translations);
    }

    /**
     * LocalizedString containing the given entry.
     */
    public LocalizedString(final Locale locale, final String value) {
        this(ImmutableMap.of(locale, value));
    }

    /**
     * LocalizedString containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public LocalizedString(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        this(ImmutableMap.of(locale1, value1, locale2, value2));
    }

    /**
     * LocalizedString containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public LocalizedString plus(final Locale locale, final String value) {
        final Map<Locale, String> newMap = new ImmutableMap.Builder<Locale, String>().
                putAll(translations).
                put(locale, value).
                build();
        return new LocalizedString(newMap);
    }

    public Optional<String> get(final Locale locale) {
        return Optional.fromNullable(translations.get(locale));
    }

    public Optional<String> get(final Iterable<Locale> locales) {
        final Locale firstAvailableLocale = Iterables.find(locales, new Predicate<Locale>() {
            @Override
            public boolean apply(@Nullable Locale input) {
                return translations.containsKey(input);
            }
        }, null);
        return get(firstAvailableLocale);
    }

    /**
     * Returns all available locales.
     */
    public Set<Locale> getLocales() {
        return translations.keySet();
    }

    @Override
    public String toString() {
        return "LocalizedString(" + Joiner.on(", ").withKeyValueSeparator(" -> ").join(translations) + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedString localized = (LocalizedString) o;
        return this.translations.equals(localized.translations);
    }

    @Override
    public int hashCode() {
        return translations.hashCode();
    }

}
