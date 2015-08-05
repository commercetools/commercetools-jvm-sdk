package io.sphere.sdk.models;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Locale;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalizedStringTest {
    private final Locale germanLocale = Locale.GERMAN;
    private final Locale englishLocale = Locale.ENGLISH;
    private final Locale frenchLocale = Locale.FRANCE;
    private final String defaultString1 = "foo";
    private final String defaultString2 = "bar";
    private final LocalizedString localizedString = LocalizedString.of(germanLocale, defaultString1, englishLocale, defaultString2);
    private final String dogFoodJson = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
    private final LocalizedString dogFood = LocalizedString.of(germanLocale, "Hundefutter", englishLocale, "dog food");
    private final LocalizedString upperCasedDogFood = LocalizedString.of(germanLocale, "HUNDEFUTTER", englishLocale, "DOG FOOD");

    @Test
    public void createFromOneValue() throws Exception {
        final LocalizedString ls = LocalizedString.of(germanLocale, defaultString1);
        assertThat(ls.get(germanLocale)).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale)).isNull();
    }

    @Test
    public void createFromTwoValues() throws Exception {
        assertThat(localizedString.get(germanLocale)).isEqualTo(defaultString1);
        assertThat(localizedString.get(englishLocale)).isEqualTo(defaultString2);
    }

    @Test
    public void createANewLocalizedStringByAddingALocale() throws Exception {
        final LocalizedString ls = LocalizedString.of(germanLocale, defaultString1).plus(englishLocale, defaultString2);
        assertThat(ls.get(germanLocale)).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale)).isEqualTo(defaultString2);
    }

    @Test
    public void findTheFirstBestTranslation() throws Exception {
        final String actual = localizedString.get(asList(frenchLocale, englishLocale, germanLocale));
        assertThat(actual).isEqualTo(defaultString2);
    }

    @Test
    public void returnPresentLocales() throws Exception {
        assertThat(localizedString.getLocales()).isEqualTo(new HashSet<>(asList(germanLocale, englishLocale)));
    }

    @Test
    public void implementToString() throws Exception {
        assertThat(localizedString.toString()).isEqualTo(format("LocalizedString(de -> %s, en -> %s)", defaultString1, defaultString2));
    }

    @Test
    public void implementEquals() throws Exception {
        assertThat(LocalizedString.of(germanLocale, defaultString1).plus(englishLocale, defaultString2).equals(localizedString)).isTrue();
        assertThat(LocalizedString.of(germanLocale, defaultString1).equals(localizedString)).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithCreator() throws Exception {
        LocalizedString.of(germanLocale, defaultString1, germanLocale, defaultString2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithPlus() throws Exception {
        LocalizedString.of(germanLocale, defaultString1).plus(germanLocale, defaultString2);
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
        final LocalizedString actual = LocalizedString.of(germanLocale, "Aa -A_", englishLocale, "dog food").slugified();
        final LocalizedString expected = LocalizedString.of(germanLocale, "aa-a_", englishLocale, "dog-food");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void slugifyUnique() throws Exception {
        final LocalizedString actual = LocalizedString.of(germanLocale, "Aa -A_", englishLocale, "dog food").slugifiedUnique();
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
