package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.sphere.sdk.utils.SphereInternalUtils.*;
import static java.lang.String.format;
import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * A localized string is a object where the keys are {@link Locale}s (HTTP API: ISO language tags),
 * and the values are the corresponding strings used for that language.
 *
 * {@include.example io.sphere.sdk.models.LocalizedStringTest#defaultUseCases()}
 */
public final class LocalizedString extends Base {

    private static final Comparator<Map.Entry<Locale, String>> BY_LOCALE_COMPARATOR = (left, right) -> left.getKey().toString().compareTo(right.getKey().toString());

    @JsonIgnore
    private final Map<Locale, String> translations;

    @JsonCreator
    private LocalizedString(final Map<Locale, String> translations) {
        //the Jackson mapper may passes null here and it is not possible to use an immutable map
        this.translations = immutableCopyOf(Optional.ofNullable(translations).orElse(Collections.emptyMap()));
    }

    /**
     * Creates an instance without any value.
     *
     * @return instance without any value
     */
    @JsonIgnore
    public static LocalizedString of() {
        return of(emptyMap());
    }

    /**
     * Creates an instance without any value.
     *
     * @return instance without any value
     */
    public static LocalizedString empty() {
        return of();
    }

    /**
     * Creates an instance with one locale translation pair.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#createFromOneValue()}
     *
     * @param locale the locale for the one translation
     * @param value the translation for the specified locale
     * @return translation for one language
     */
    @JsonIgnore
    public static LocalizedString of(final Locale locale, final String value) {
        requireNonNull(locale);
        requireNonNull(value);
        return of(mapOf(locale, value));
    }

    /**
     * Creates an instance for two different locales.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#createFromTwoValues()}
     *
     * @param locale1 the first locale
     * @param value1 the translation corresponding to {@code locale1}
     * @param locale2 the second locale which differs from {@code locale1}
     * @param value2 the translation corresponding to {@code locale2}
     * @return new instance for two key value pairs
     */
    @JsonIgnore
    public static LocalizedString of(final Locale locale1, final String value1, final Locale locale2, final String value2) {
        return of(mapOf(locale1, value1, locale2, value2));
    }

    /**
     * Creates an instance by supplying a map of {@link Locale} and {@link String}. Changes to the map won't affect the instance.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#createByMap()}
     *
     * @param translations the key value pairs for the translation
     * @return a new instance which has the same key value pairs as {@code translation} at creation time
     */
    @JsonIgnore
    public static LocalizedString of(final Map<Locale, String> translations) {
        requireNonNull(translations);
        return new LocalizedString(translations);
    }

    /**
     * Creates an instance by supplying a map of {@link String} the language tag and {@link String}. Changes to the map won't affect the instance.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#ofStringToStringMap()}
     *
     * @param translations the key value pairs for the translation
     * @return a new instance which has the same key value pairs as {@code translation} at creation time
     */
    @JsonIgnore
    public static LocalizedString ofStringToStringMap(final Map<String, String> translations) {
        requireNonNull(translations);
        return translations.entrySet().stream()
                .map(localeEntry -> {
                    final Locale locale = Locale.forLanguageTag(localeEntry.getKey());
                    return LocalizedStringEntry.of(locale, localeEntry.getValue());
                })
                .collect(LocalizedString.streamCollector());
    }

    /**
     * Creates a new {@link LocalizedString} containing the given entries and the new one.
     * It is not allowed to override existing entries.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#createANewLocalizedStringByAddingALocale()}
     *
     * @param locale the additional locale of the new entry
     * @param value the value for the <code>locale</code>
     * @return a LocalizedString containing this data and the from the parameters.
     * @throws IllegalArgumentException if duplicate locales are provided
     */
    public LocalizedString plus(final Locale locale, final String value) {
        if (translations.containsKey(locale)) {
            throw new IllegalArgumentException(format("Duplicate keys (%s) for map creation.", locale));
        }
        final Map<Locale, String> newMap = new HashMap<>();
        newMap.putAll(translations);
        newMap.put(locale, value);
        return new LocalizedString(newMap);
    }

    /**
     * Searches the translation for an exact locale and returning the result in an {@link Optional}.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#find()}
     *
     * @param locale the locale which should be searched
     * @return A filled optional with the translation belonging to {@code locale} or an empty optional if the locale is not present.
     */
    @Nonnull
    public Optional<String> find(final Locale locale) {
        return Optional.ofNullable(get(locale));
    }

    /**
     * Searches the translation for an exact locale by using {@code null} in the case the locale ist not present.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#getByOneLocale()}
     *
     * @param locale the locale which should be searched
     * @return the translation belonging to {@code locale} or null if the locale is not present.
     */
    @Nullable
    public String get(final Locale locale) {
        return translations.get(locale);
    }

    /**
     * Searches the translation for a locale specified in IETF BCP 47 by language tag string.
     * If the specified language tag contains any ill-formed subtags, the first such subtag and all following subtags are ignored.
     *
     * @param languageTag the IETF language tag corresponding to an {@code Locale}
     * @return the translation belonging to {@code languageTag} or {@code null} if the locale is not present.
     */
    @Nullable
    public String get(final String languageTag){
        final Locale locale = Locale.forLanguageTag(languageTag);
        return get(locale);
    }

    /**
     * Searches the translation for some exact locales in the order they appear and returning the result in an {@link Optional}.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#findByMultipleLocales()}
     *
     * @param locales the locale which should be searched, the first exact match wins
     * @return A filled optional with the translation belonging to one of the {@code locales} or an empty optional if none of the locales is not present.
     */
    @Nonnull
    public Optional<String> find(final Iterable<Locale> locales) {
        final Optional<Locale> firstFoundLocale = toStream(locales).filter(locale -> translations.containsKey(locale)).findFirst();
        return firstFoundLocale.map(foundLocale -> get(foundLocale));
    }

    /**
     * Searches the translation for some exact locales in the order they appear and using null as result if no match could be found.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#getByMultipleLocales()}
     *
     * @param locales the locale which should be searched, the first exact match wins
     * @return the translation belonging to one of the {@code locales} or null if none of the locales is not present.
     */
    @Nullable
    public String get(final Iterable<Locale> locales) {
        return find(locales).orElse(null);
    }

    /**
     * Searches a translation which matches a locale in {@code locales} and uses language fallbackes.
     * If locales which countries are used then the algorithm searches also for the pure language locale.
     * So if "en_US" could not be found then "en" will be tried.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#getTranslation()}
     *
     * @param locales the locales to try out
     * @return a translation matching one of the locales or null
     */
    @Nullable
    public String getTranslation(final Iterable<Locale> locales) {
        return StreamSupport.stream(locales.spliterator(), false)
                .map(localeToFind -> {
                    String match = get(localeToFind);
                    if (match == null) {
                        final Locale pureLanguageLocale = new Locale(localeToFind.getLanguage());
                        match = get(pureLanguageLocale);
                    }
                    return match;
                })
                .filter(x -> x != null)
                .findFirst()
                .orElse(null);
    }

    /**
     * Creates a new instance where each translation value is transformed with {@code function}.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#mapValue()}
     *
     * @param function transforms a value for a locale into a new value
     * @return a new {@link LocalizedString} which consist all elements for this transformed with {@code function}
     */
    public LocalizedString mapValue(final BiFunction<Locale, String, String> function) {
        return stream().map(entry -> {
            final String newValue = function.apply(entry.getLocale(), entry.getValue());
            return LocalizedStringEntry.of(entry.getLocale(), newValue);
        }).collect(streamCollector());
    }

    /**
     * Creates a new Stream of entries.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#streamAndCollector()}
     *
     * @return stream of all entries
     */
    public Stream<LocalizedStringEntry> stream() {
        return translations.entrySet().stream().map(entry -> LocalizedStringEntry.of(entry.getKey(), entry.getValue()));
    }

    /**
     * Collector to collect a stream of {@link LocalizedStringEntry}s to one {@link LocalizedString}.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#streamAndCollector()}
     *
     * @return collector
     */
    public static Collector<LocalizedStringEntry, ?, LocalizedString> streamCollector() {
        final Collector<LocalizedStringEntry, Map<Locale, String>, LocalizedString> result =
                Collector.of(HashMap::new, (Map<Locale, String> tmpMap, LocalizedStringEntry entry) -> tmpMap.put(entry.getLocale(), entry.getValue()), (Map<Locale, String> left, Map<Locale, String> right) -> {
                    left.putAll(right);
                    return left;
                }, (Map<Locale, String> entryMap) -> LocalizedString.of(entryMap));
        return result;
    }

    /**
     * Creates a new {@link LocalizedString} where all translations are slugified (remove whitespace, etc.).
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#slugified()}
     *
     * @return new instance
     */
    public LocalizedString slugified() {
        return mapValue((locale, value) -> slugify(value));
    }

    /**
     * Creates a new {@link LocalizedString} where all translations are slugified (remove whitespace, etc.) and a random supplement is added.
     * This slugify methods appends a random string for a little uniqueness.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#slugifyUniqueDemo()}
     *
     * @return new instance
     */
    public LocalizedString slugifiedUnique() {
        return mapValue((locale, value) -> slugifyUnique(value));
    }

    /**
     * Returns all locales included in this instance.
     *
     * {@include.example io.sphere.sdk.models.LocalizedStringTest#getLocales()}
     * @return locales
     */
    @JsonIgnore
    @Nonnull
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
    private void set(final String languageTag, final String value) {
        translations.put(Locale.forLanguageTag(languageTag), value);
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    public static TypeReference<LocalizedString> typeReference() {
        return new TypeReference<LocalizedString>() {
            @Override
            public String toString() {
                return "TypeReference<LocalizedString>";
            }
        };
    }

    public static LocalizedString ofEnglish(final String translationForEnglish) {
        requireNonNull(translationForEnglish);
        return of(Locale.ENGLISH, translationForEnglish);
    }
}
