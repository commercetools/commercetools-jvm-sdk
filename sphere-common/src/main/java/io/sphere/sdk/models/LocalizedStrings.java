package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.utils.ImmutableMapBuilder;
import io.sphere.sdk.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.MapUtils.*;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * A wrapper around an attribute which can be translated into a number of locales.
 * Note that even if your project only uses one language some attributes (name and description for example) will be
 * always be LocalizedStrings.
 */
public class LocalizedStrings extends Base {

    private static final Comparator<Map.Entry<Locale, String>> BY_LOCALE_COMPARATOR = (left, right) -> left.getKey().toString().compareTo(right.getKey().toString());

    @JsonIgnore
    private final Map<Locale, String> translations;

    @JsonCreator
    private LocalizedStrings(final Map<Locale, String> translations) {
        //the Jackson mapper passes null here and it is not possible to use an immutable map
        this.translations = copyOf(Optional.ofNullable(translations).orElse(new HashMap<>()));
    }

    /**
     * LocalizedStrings containing the given entry.
     * @param locale the locale of the new entry
     * @param value the value for the <code>locale</code>
     */
    @JsonIgnore
    private LocalizedStrings(final Locale locale, final String value) {
        this(mapOf(locale, value));
    }

    /**
     * LocalizedStrings containing the 2 entries.
     * @param locale1 the locale for the first entry
     * @param value1 the value for the first entry
     * @param locale2 the locale for the second entry
     * @param value2 the value for the second entry
     * @throws IllegalArgumentException if duplicate locales are provided
     */
    @JsonIgnore
    private LocalizedStrings(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        this(mapOf(locale1, value1, locale2, value2));
    }

    /**
     * Creates an instance without any value.
     *
     * @return instance without any value
     */
    @JsonIgnore
    public static LocalizedStrings of() {
        return of(new HashMap<>());
    }

    /**
     * Creates an instance without any value.
     *
     * @return instance without any value
     */
    public static LocalizedStrings empty() {
        return of();
    }

    @JsonIgnore
    public static LocalizedStrings of(final Locale locale, final String value) {
        requireNonNull(locale);
        requireNonNull(value);
        return new LocalizedStrings(locale, value);
    }

    @JsonIgnore
    public static LocalizedStrings of(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        return new LocalizedStrings(mapOf(locale1, value1, locale2, value2));
    }

    @JsonIgnore
    public static LocalizedStrings of(final Map<Locale, String> translations) {
        requireNonNull(translations);
        return new LocalizedStrings(translations);
    }

    @JsonIgnore
    public static LocalizedStrings ofEnglishLocale(final String value) {
        return of(Locale.ENGLISH, value);
    }

    /**
     * LocalizedStrings containing the given entries.
     * @param locale the additional locale of the new entry
     * @param value the value for the <code>locale</code>
     * @return a LocalizedStrings containing this data and the from the parameters.
     * @throws IllegalArgumentException if duplicate locales are provided
     */
    public LocalizedStrings plus(final Locale locale, final String value) {
        if (translations.containsKey(locale)) {
            throw new IllegalArgumentException(format("Duplicate keys (%s) for map creation.", locale));
        }
        final Map<Locale, String> newMap = ImmutableMapBuilder.<Locale, String>of().
                putAll(translations).
                put(locale, value).
                build();
        return new LocalizedStrings(newMap);
    }

    public Optional<String> find(final Locale locale) {
        return Optional.ofNullable(get(locale));
    }

    @Nullable
    public String get(final Locale locale) {
        return translations.get(locale);
    }

    public Optional<String> find(final Iterable<Locale> locales) {
        final Optional<Locale> firstFoundLocale = toStream(locales).filter(locale -> translations.containsKey(locale)).findFirst();
        return firstFoundLocale.map(foundLocale -> get(foundLocale));
    }

    @Nullable
    public String get(final Iterable<Locale> locales) {
        return find(locales).orElse(null);
    }

    /**
     * Creates a new instance where each translation value is transformed with {@code function}.
     * @param function transforms a value for a locale into a new value
     * @return a new {@link LocalizedStrings} which consist all elements for this transformed with {@code function}
     */
    public LocalizedStrings mapValue(final BiFunction<Locale, String, String> function) {
        return stream().map(entry -> {
            final String newValue = function.apply(entry.getLocale(), entry.getValue());
            return LocalizedStringsEntry.of(entry.getLocale(), newValue);
        }).collect(streamCollector());
    }

    public Stream<LocalizedStringsEntry> stream() {
        return translations.entrySet().stream().map(entry -> LocalizedStringsEntry.of(entry.getKey(), entry.getValue()));
    }

    public static Collector<LocalizedStringsEntry, ?, LocalizedStrings> streamCollector() {
        final Collector<LocalizedStringsEntry, Map<Locale, String>, LocalizedStrings> result =
                Collector.of(HashMap::new, (Map<Locale, String> tmpMap, LocalizedStringsEntry entry) -> tmpMap.put(entry.getLocale(), entry.getValue()), (Map<Locale, String> left, Map<Locale, String> right) -> {
                    left.putAll(right);
                    return left;
                }, (Map<Locale, String> entryMap) -> LocalizedStrings.of(entryMap));
        return result;
    }

    /**
     * Creates a new {@link LocalizedStrings} where all translations are slugified (remove whitespace, etc.).
     * @return new instance
     */
    public LocalizedStrings slugified() {
        return mapValue((locale, value) -> StringUtils.slugify(value));
    }

    /**
     * Creates a new {@link LocalizedStrings} where all translations are slugified (remove whitespace, etc.).
     * This slugify methods appends a random string for a little uniqueness.
     * @return new instance
     */
    public LocalizedStrings slugifiedUnique() {
        return mapValue((locale, value) -> StringUtils.slugifyUnique(value));
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
        return "LocalizedStrings(" +
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
    private void set(final String languageTag, final String value) {
        translations.put(Locale.forLanguageTag(languageTag), value);
    }

    public static TypeReference<LocalizedStrings> typeReference() {
        return new TypeReference<LocalizedStrings>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedStrings>";
            }
        };
    }
}
