package io.sphere.sdk.models;

import io.sphere.sdk.json.SphereJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LocalizedStringTest {
    private static final String FULL_LOCALE_JSON_STRING = "{\"en-GB\":\"children\",\"en-US\":\"kids\"}";
    private static final String DEFAULT_STRING_1 = "foo";
    private static final String DEFAULT_STRING_2 = "bar";
    private static final LocalizedString LOCALIZED_STRING = LocalizedString.of(Locale.GERMAN, DEFAULT_STRING_1, Locale.ENGLISH, DEFAULT_STRING_2);
    private static final String DOG_FOOD_JSON = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";

    @Test
    public void createFromOneValue() throws Exception {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter");
        assertThat(ls.getLocales()).containsExactly(Locale.GERMAN);
        assertThat(ls.get(Locale.GERMAN)).isEqualTo("Hundefutter");
        assertThat(ls.get(Locale.ENGLISH)).isNull();
    }

    @Test
    public void createFromTwoValues() throws Exception {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");
        assertThat(ls.getLocales()).containsExactly(Locale.GERMAN, Locale.ENGLISH);
        assertThat(ls.get(Locale.GERMAN)).isEqualTo("Hundefutter");
        assertThat(ls.get(Locale.ENGLISH)).isEqualTo("dog food");
    }

    @Test
    public void createByMap() {
        final Map<Locale, String> map = new HashMap<>();
        map.put(Locale.GERMAN, "Hundefutter");
        map.put(Locale.ENGLISH, "dog food");
        final LocalizedString ls = LocalizedString.of(map);

        assertThat(ls.getLocales()).containsExactly(Locale.GERMAN, Locale.ENGLISH);
        assertThat(ls).isEqualTo(LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food"));

        map.remove(Locale.GERMAN);
        map.put(Locale.forLanguageTag("es"), "Comida para perro");

        assertThat(ls.getLocales())
                .overridingErrorMessage("changes in the creation map do not change the created object")
                .containsExactly(Locale.GERMAN, Locale.ENGLISH);
        assertThat(ls).isEqualTo(LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food"));
        assertThat(ls.get(Locale.GERMAN)).isEqualTo("Hundefutter");
    }

    @Test
    public void ofEnglishLocale() {
        assertThat(LocalizedString.ofEnglish("dog food"))
                .isEqualTo(LocalizedString.of(Locale.ENGLISH, "dog food"));
    }

    @Test
    public void createANewLocalizedStringByAddingALocale() throws Exception {
        final LocalizedString singleGermanEntryLocalizedString = LocalizedString.of(Locale.GERMAN, "Hundefutter");

        final LocalizedString twoEntriesLocalizedString = singleGermanEntryLocalizedString.plus(Locale.ENGLISH, "dog food");

        assertThat(twoEntriesLocalizedString.get(Locale.GERMAN)).isEqualTo("Hundefutter");
        assertThat(twoEntriesLocalizedString.get(Locale.ENGLISH)).isEqualTo("dog food");
        assertThat(singleGermanEntryLocalizedString.get(Locale.GERMAN)).isEqualTo("Hundefutter");
        assertThat(singleGermanEntryLocalizedString.get(Locale.ENGLISH))
                .overridingErrorMessage("the original instance did not change")
                .isNull();
        assertThatThrownBy(() -> singleGermanEntryLocalizedString.plus(Locale.GERMAN, "override"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findTheFirstBestTranslation() throws Exception {
        final String actual = LOCALIZED_STRING.get(asList(Locale.FRANCE, Locale.ENGLISH, Locale.GERMAN));
        assertThat(actual).isEqualTo(DEFAULT_STRING_2);
    }

    @Test
    public void find() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter");

        final Optional<String> germanTranslation = ls.find(Locale.GERMAN);
        assertThat(germanTranslation).isEqualTo(Optional.of("Hundefutter"));

        final Optional<String> englishTranslation = ls.find(Locale.ENGLISH);
        assertThat(englishTranslation).isEqualTo(Optional.empty());
    }

    @Test
    public void getByOneLocale() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter");

        final String germanTranslation = ls.get(Locale.GERMAN);
        assertThat(germanTranslation).isNotNull().isEqualTo("Hundefutter");

        final String englishTranslation = ls.get(Locale.ENGLISH);
        assertThat(englishTranslation).isNull();
    }

    @Test
    public void findByMultipleLocales() {
        final LocalizedString ls = LocalizedString
                .of(Locale.GERMAN, "de")
                .plus(Locale.ENGLISH, "en")
                .plus(Locale.US, "en_US");

        assertThat(ls.find(asList(Locale.US, Locale.ENGLISH))).isEqualTo(Optional.of("en_US"));
        assertThat(ls.find(asList(Locale.FRENCH, Locale.ENGLISH))).isEqualTo(Optional.of("en"));
        assertThat(ls.find(asList(Locale.UK, Locale.ENGLISH))).isEqualTo(Optional.of("en"));
        assertThat(ls.find(asList(Locale.UK, Locale.GERMAN)))
                .overridingErrorMessage("no automatic fallback to plain English")
                .isEqualTo(Optional.of("de"));
        assertThat(ls.find(asList(Locale.UK, Locale.CHINESE))).isEqualTo(Optional.empty());
    }

    @Test
    public void getByMultipleLocales() {
        final LocalizedString ls = LocalizedString
                .of(Locale.GERMAN, "de")
                .plus(Locale.ENGLISH, "en")
                .plus(Locale.US, "en_US");

        assertThat(ls.get(asList(Locale.US, Locale.ENGLISH))).isEqualTo("en_US");
        assertThat(ls.get(asList(Locale.FRENCH, Locale.ENGLISH))).isEqualTo("en");
        assertThat(ls.get(asList(Locale.UK, Locale.ENGLISH))).isEqualTo("en");
        assertThat(ls.get(asList(Locale.UK, Locale.GERMAN)))
                .overridingErrorMessage("no automatic fallback to plain English")
                .isEqualTo("de");
    }

    @Test
    public void getTranslation() {
        final LocalizedString ls = LocalizedString
                .of(Locale.GERMAN, "de")
                .plus(Locale.ENGLISH, "en")
                .plus(Locale.US, "en_US");

        assertThat(ls.getTranslation(asList(Locale.US, Locale.ENGLISH))).isEqualTo("en_US");
        assertThat(ls.getTranslation(asList(Locale.ENGLISH, Locale.US))).isEqualTo("en");
        assertThat(ls.getTranslation(asList(Locale.FRENCH, Locale.ENGLISH))).isEqualTo("en");
        assertThat(ls.getTranslation(asList(Locale.UK, Locale.ENGLISH))).isEqualTo("en");
        assertThat(ls.getTranslation(asList(Locale.GERMAN, Locale.UK))).isEqualTo("de");
        assertThat(ls.getTranslation(asList(Locale.UK, Locale.GERMAN)))
                .overridingErrorMessage(
                        "the plain language fallback wins also in the case after that comes a concrete match")
                .isEqualTo("en");
        assertThat(ls.getTranslation(asList(Locale.FRENCH, Locale.UK, Locale.GERMAN)))
                .overridingErrorMessage("automatic fallback to plain English")
                .isEqualTo("en");
        assertThat(LocalizedString.of(Locale.US, "en_US").getTranslation(asList(Locale.ENGLISH)))
                .overridingErrorMessage("no fallback to locale with country")
                .isNull();

        assertThat(ls.getTranslation(asList(Locale.UK, Locale.US, Locale.GERMAN)))
                .isEqualTo(ls.get(asList(Locale.UK, Locale.ENGLISH, Locale.US, Locale.ENGLISH, Locale.GERMAN)));

        assertThat(ls.get("en"))
                .isEqualTo(ls.get(Locale.ENGLISH))
                .isEqualTo(ls.get("en-***")) //-*** is illegal (ietf bcp 47) and will be ignored
                .isNotEqualTo(ls.get(Locale.US));
        assertThat(ls.get("de"))
                .isEqualTo(ls.get(Locale.GERMAN))
                .isNotEqualTo(ls.get("de-AT"));
        assertThat(ls.get("en-US"))
                .isEqualTo(ls.get(Locale.US))
                .isEqualTo(ls.get("en-US-blub")) //-blub is illegal (ietf bcp 47) and will be ignored
                .isNotEqualTo(ls.get("en-US-u-islamcal")); //-u-islamcal legal extension (ietf bcp 47)
        assertThat(ls.get("en_US"))
                .isNull();
        assertThat(ls.get("xxx"))
                .isNull();
    }

    @Test
    public void returnPresentLocales() throws Exception {
        assertThat(LOCALIZED_STRING.getLocales()).isEqualTo(new HashSet<>(asList(Locale.GERMAN, Locale.ENGLISH)));
    }

    @Test
    public void implementToString() throws Exception {
        assertThat(LOCALIZED_STRING.toString()).isEqualTo(format("LocalizedString(de -> %s, en -> %s)", DEFAULT_STRING_1, DEFAULT_STRING_2));
    }

    @Test
    public void implementEquals() throws Exception {
        assertThat(LocalizedString.of(Locale.GERMAN, DEFAULT_STRING_1).plus(Locale.ENGLISH, DEFAULT_STRING_2).equals(LOCALIZED_STRING)).isTrue();
        assertThat(LocalizedString.of(Locale.GERMAN, DEFAULT_STRING_1).equals(LOCALIZED_STRING)).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithCreator() throws Exception {
        LocalizedString.of(Locale.GERMAN, DEFAULT_STRING_1, Locale.GERMAN, DEFAULT_STRING_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithPlus() throws Exception {
        LocalizedString.of(Locale.GERMAN, DEFAULT_STRING_1).plus(Locale.GERMAN, DEFAULT_STRING_2);
    }

    @Test
    public void jsonSerialize() throws Exception {
        assertThat(SphereJsonUtils.newObjectMapper().writeValueAsString(LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food"))).isEqualTo(DOG_FOOD_JSON);
    }

    @Test
    public void jsonDeserialize() throws Exception {
        assertThat(SphereJsonUtils.newObjectMapper().readValue(DOG_FOOD_JSON, LocalizedString.class)).isEqualTo(LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food"));
    }

    @Test
    public void slugify() throws Exception {
        final LocalizedString actual = LocalizedString.of(Locale.GERMAN, "Aa -A_", Locale.ENGLISH, "dog food").slugified();
        final LocalizedString expected = LocalizedString.of(Locale.GERMAN, "aa-a_", Locale.ENGLISH, "dog-food");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void slugifyRespectsAllowedCharacters() {
        final String actual = LocalizedString.ofEnglish("Pick + Mix 18 Volt").slugified().get(Locale.ENGLISH);
        final String expected = "pick-mix-18-volt";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void slugifyUniqueRespectsAllowedLength() {
        final int allowedLength = 256;
        final String tooLongString = StringUtils.repeat("a", allowedLength + 1);
        assertThat(tooLongString).hasSize(allowedLength + 1);
        final String actual = LocalizedString.ofEnglish(tooLongString).slugifiedUnique().get(Locale.ENGLISH);
        assertThat(actual).hasSize(allowedLength).matches("a{247}-\\d{8}");
    }

    @Test
    public void slugifyRespectsAllowedLength() {
        final int allowedLength = 256;
        final String tooLongString = StringUtils.repeat("a", allowedLength - 1) + "bc";
        assertThat(tooLongString).hasSize(allowedLength + 1);
        final String actual = LocalizedString.ofEnglish(tooLongString).slugified().get(Locale.ENGLISH);
        assertThat(actual).hasSize(allowedLength).endsWith("ab");
    }

    @Test
    public void slugifyUnique() throws Exception {
        final LocalizedString actual = LocalizedString.of(Locale.GERMAN, "Aa -A_", Locale.ENGLISH, "dog food").slugifiedUnique();
        assertThat(actual.stream().allMatch(entry -> entry.getValue().matches("[\\w-]+-\\d{6,14}"))).isTrue();
    }

    @Test
    public void slugifyUniqueDemo() throws Exception {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");

        final LocalizedString slugifiedUnique = ls.slugifiedUnique();

        assertThat(slugifiedUnique.get(Locale.GERMAN)).matches("hundefutter-\\d+");//example: hundefutter-62899407
        assertThat(slugifiedUnique.get(Locale.ENGLISH)).matches("dog-food-\\d+");
    }

    @Test
    public void stream() throws Exception {
        final LocalizedString upperCased = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food")
                .stream()
                .map(e -> LocalizedStringEntry.of(e.getLocale(), e.getValue().toUpperCase()))
                .collect(LocalizedString.streamCollector());
        assertThat(upperCased).isEqualTo(LocalizedString.of(Locale.GERMAN, "HUNDEFUTTER", Locale.ENGLISH, "DOG FOOD"));
    }

    @Test
    public void mapValue() throws Exception {
        final LocalizedString upperCased = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food")
                .mapValue((locale, value) -> value.toUpperCase(locale));
        assertThat(upperCased).isEqualTo(LocalizedString.of(Locale.GERMAN, "HUNDEFUTTER", Locale.ENGLISH, "DOG FOOD"));
    }

    @Test
    public void streamAndCollector() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Company Hundefutter", Locale.ENGLISH, "Company dog food");

        final Stream<LocalizedStringEntry> stream = ls.stream();

        final LocalizedString updatedLs = stream
                .map(entry -> {
                    final Locale locale = entry.getLocale();
                    final String value = entry.getValue();
                    final String newValue = value.replace("Company ", "");
                    return LocalizedStringEntry.of(locale, newValue);
                })
                .collect(LocalizedString.streamCollector());
        assertThat(updatedLs).isEqualTo(LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food"));
    }

    @Test
    public void defaultUseCases() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");

        assertThat(ls.getTranslation(singletonList(Locale.US))).isEqualTo("dog food");//fuzzy search
        assertThat(ls.getTranslation(singletonList(Locale.ENGLISH))).isEqualTo("dog food");//strict search
        assertThat(ls.getLocales()).isEqualTo(new HashSet<>(asList(Locale.GERMAN, Locale.ENGLISH)));//inspecting locales
        assertThat(ls.slugified())//slugified values for urls
                .isEqualTo(LocalizedString.of(Locale.GERMAN, "hundefutter", Locale.ENGLISH, "dog-food"));
        final LocalizedString slugifiedUnique = ls.slugifiedUnique();
        assertThat(slugifiedUnique.get(Locale.GERMAN)).matches("hundefutter-\\d+");//example: hundefutter-62899407
        assertThat(slugifiedUnique.get(Locale.ENGLISH)).matches("dog-food-\\d+");
    }

    @Test
    public void slugified() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");
        assertThat(ls.slugified())
                .isEqualTo(LocalizedString.of(Locale.GERMAN, "hundefutter", Locale.ENGLISH, "dog-food"));
    }

    @Test
    public void getLocales() {
        final LocalizedString ls = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");
        final Set<Locale> locales = ls.getLocales();
        assertThat(locales)
                .isEqualTo(new HashSet<>(asList(Locale.GERMAN, Locale.ENGLISH)));
    }

    @Test
    public void serializeWithFullLocale() {
        final LocalizedString localizedString = SphereJsonUtils.readObject(FULL_LOCALE_JSON_STRING, LocalizedString.typeReference());
        assertThat(localizedString.get(Locale.US)).isEqualTo("kids");
        assertThat(localizedString.get(Locale.UK)).isEqualTo("children");
    }

    @Test
    public void deserializeWithFullLocale() {
        final LocalizedString localizedString = LocalizedString.of(Locale.US, "kids", Locale.UK, "children");
        assertThat(SphereJsonUtils.toJsonNode(localizedString)).isEqualTo(SphereJsonUtils.parse(FULL_LOCALE_JSON_STRING));
    }

    @Test
    public void ofStringToStringMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("de", "Jacken");
        map.put("en", "Jackets");
        map.put("it", "Giacche");
        final LocalizedString actual = LocalizedString.ofStringToStringMap(map);
        final LocalizedString expected = LocalizedString
                .of(Locale.GERMAN, "Jacken")
                .plus(Locale.ENGLISH, "Jackets")
                .plus(Locale.ITALIAN, "Giacche");
        assertThat(actual).isEqualTo(expected);
    }
}
