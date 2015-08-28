package io.sphere.sdk.models;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.*;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThatThrownBy;

public class LocalizedStringTest {
    private final String defaultString1 = "foo";
    private final String defaultString2 = "bar";
    private final LocalizedString localizedString = LocalizedString.of(Locale.GERMAN, defaultString1, Locale.ENGLISH, defaultString2);
    private final String dogFoodJson = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
    private final LocalizedString dogFood = LocalizedString.of(Locale.GERMAN, "Hundefutter", Locale.ENGLISH, "dog food");
    private final LocalizedString upperCasedDogFood = LocalizedString.of(Locale.GERMAN, "HUNDEFUTTER", Locale.ENGLISH, "DOG FOOD");

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
        assertThat(LocalizedString.ofEnglishLocale("dog food"))
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
        final String actual = localizedString.get(asList(Locale.FRANCE, Locale.ENGLISH, Locale.GERMAN));
        assertThat(actual).isEqualTo(defaultString2);
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
    }

    @Test
    public void returnPresentLocales() throws Exception {
        assertThat(localizedString.getLocales()).isEqualTo(new HashSet<>(asList(Locale.GERMAN, Locale.ENGLISH)));
    }

    @Test
    public void implementToString() throws Exception {
        assertThat(localizedString.toString()).isEqualTo(format("LocalizedString(de -> %s, en -> %s)", defaultString1, defaultString2));
    }

    @Test
    public void implementEquals() throws Exception {
        assertThat(LocalizedString.of(Locale.GERMAN, defaultString1).plus(Locale.ENGLISH, defaultString2).equals(localizedString)).isTrue();
        assertThat(LocalizedString.of(Locale.GERMAN, defaultString1).equals(localizedString)).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithCreator() throws Exception {
        LocalizedString.of(Locale.GERMAN, defaultString1, Locale.GERMAN, defaultString2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithPlus() throws Exception {
        LocalizedString.of(Locale.GERMAN, defaultString1).plus(Locale.GERMAN, defaultString2);
    }

    @Test
    public void jsonSerialize() throws Exception {
        assertThat(SphereJsonUtils.newObjectMapper().writeValueAsString(dogFood)).isEqualTo(dogFoodJson);
    }

    @Test
    public void jsonDeserialize() throws Exception {
        assertThat(SphereJsonUtils.newObjectMapper().readValue(dogFoodJson, LocalizedString.class)).isEqualTo(dogFood);
    }

    @Test
    public void slugify() throws Exception {
        final LocalizedString actual = LocalizedString.of(Locale.GERMAN, "Aa -A_", Locale.ENGLISH, "dog food").slugified();
        final LocalizedString expected = LocalizedString.of(Locale.GERMAN, "aa-a_", Locale.ENGLISH, "dog-food");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void slugifyUnique() throws Exception {
        final LocalizedString actual = LocalizedString.of(Locale.GERMAN, "Aa -A_", Locale.ENGLISH, "dog food").slugifiedUnique();
        assertThat(actual.stream().allMatch(entry -> entry.getValue().matches("[\\w-]+-\\d{6,14}"))).isTrue();
    }

    @Test
    public void stream() throws Exception {
        final LocalizedString upperCased = dogFood.stream()
                .map(e -> LocalizedStringEntry.of(e.getLocale(), e.getValue().toUpperCase()))
                .collect(LocalizedString.streamCollector());
        assertThat(upperCased).isEqualTo(upperCasedDogFood);
    }

    @Test
    public void mapValue() throws Exception {
        final LocalizedString upperCased = dogFood.mapValue((locale, value) -> value.toUpperCase());
        assertThat(upperCased).isEqualTo(upperCasedDogFood);
    }
}
