package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;
import io.sphere.sdk.utils.ImmutableMapBuilder;

import java.util.*;

import static io.sphere.sdk.utils.IterableUtils.*;
import static io.sphere.sdk.utils.MapUtils.*;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * A wrapper around an attribute which can be translated into a number of locales.
 * Note that even if your project only uses one language some attributes (name and description for example) will be
 * always be LocalizedString.
 */
public class LocalizedString {

    private static final Comparator<Map.Entry<Locale, String>> BY_LOCALE_COMPARATOR = (left, right) -> left.getKey().toString().compareTo(right.getKey().toString());

    @JsonIgnore
    private final Map<Locale, String> translations;

    @JsonCreator
    private LocalizedString(final Map<Locale, String> translations) {
        //the Jackson mapper passes null here and it is not possible to use an immutable map
        this.translations = copyOf(Optional.ofNullable(translations).orElse(new HashMap<>()));
    }

    /**
     * LocalizedString containing the given entry.
     * @param locale the locale of the new entry
     * @param value the value for the <code>locale</code>
     */
    @JsonIgnore
    private LocalizedString(final Locale locale, final String value) {
        this(mapOf(locale, value));
    }

    /**
     * LocalizedString containing the 2 entries.
     * @param locale1 the locale for the first entry
     * @param value1 the value for the first entry
     * @param locale2 the locale for the second entry
     * @param value2 the value for the second entry
     * @throws IllegalArgumentException if duplicate locales are provided
     */
    @JsonIgnore
    private LocalizedString(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        this(mapOf(locale1, value1, locale2, value2));
    }

    @JsonIgnore
    public static LocalizedString of(final Locale locale, final String value) {
        return new LocalizedString(locale, value);
    }

    @JsonIgnore
    public static LocalizedString of(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        return new LocalizedString(mapOf(locale1, value1, locale2, value2));
    }

    @JsonIgnore
    public static LocalizedString of(final Map<Locale, String> translations) {
        requireNonNull(translations);
        return new LocalizedString(translations);
    }

    /**
     * LocalizedString containing the given entries.
     * @param locale the additional locale of the new entry
     * @param value the value for the <code>locale</code>
     * @return a LocalizedString containing this data and the from the parameters.
     * @throws IllegalArgumentException if duplicate locales are provided
     */
    public LocalizedString plus(final Locale locale, final String value) {
        if (translations.containsKey(locale)) {
            throw new IllegalArgumentException(format("Duplicate keys (%s) for map creation.", locale));
        }
        final Map<Locale, String> newMap = ImmutableMapBuilder.<Locale, String>of().
                putAll(translations).
                put(locale, value).
                build();
        return new LocalizedString(newMap);
    }

    public Optional<String> get(final Locale locale) {
        return Optional.ofNullable(translations.get(locale));
    }

    public Optional<String> get(final Iterable<Locale> locales) {
        final Optional<Locale> firstFoundLocale = toStream(locales).filter(locale -> translations.containsKey(locale)).findFirst();
        return firstFoundLocale.flatMap(foundLocale -> get(foundLocale));
    }

    @JsonIgnore
    public Set<Locale> getLocales() {
        return translations.keySet();
    }

    /**
     * Delivers an immutable map of the translation.
     *
     * @return the key-value pairs for the translation
     */
    @JsonAnyGetter//@JsonUnwrap supports not maps, but this construct puts map content on top level
    private Map<Locale, String> getTranslations() {
        return immutableCopyOf(translations);
    }

    @Override
    public String toString() {
        return "LocalizedString(" +
                translations
                        .entrySet()
                        .stream()
                        .sorted(BY_LOCALE_COMPARATOR)
                        .map(entry -> entry.getKey() + " -> " + entry.getValue())
                        .collect(joining(", "))
                + ")";
    }

    @SuppressWarnings("unused")//used by Jackson JSON mapper
    @JsonAnySetter
    private void set(final String languageTag, String value) {
        translations.put(Locale.forLanguageTag(languageTag), value);
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
