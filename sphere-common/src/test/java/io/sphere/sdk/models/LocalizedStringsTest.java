package io.sphere.sdk.models;

import io.sphere.sdk.json.JsonUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalizedStringsTest {
    private final Locale germanLocale = Locale.GERMAN;
    private final Locale englishLocale = Locale.ENGLISH;
    private final Locale frenchLocale = Locale.FRANCE;
    private final String defaultString1 = "foo";
    private final String defaultString2 = "bar";
    private final LocalizedStrings localizedStrings = LocalizedStrings.of(germanLocale, defaultString1, englishLocale, defaultString2);
    private final String dogFoodJson = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
    private final LocalizedStrings dogFood = LocalizedStrings.of(germanLocale, "Hundefutter", englishLocale, "dog food");
    private final LocalizedStrings upperCasedDogFood = LocalizedStrings.of(germanLocale, "HUNDEFUTTER", englishLocale, "DOG FOOD");

    @Test
    public void createFromOneValue() throws Exception {
        final LocalizedStrings ls = LocalizedStrings.of(germanLocale, defaultString1);
        assertThat(ls.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale)).isEqualTo(Optional.empty());
    }

    @Test
    public void createFromTwoValues() throws Exception {
        assertThat(localizedStrings.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(localizedStrings.get(englishLocale).get()).isEqualTo(defaultString2);
    }

    @Test
    public void createANewLocalizedStringsByAddingALocale() throws Exception {
        final LocalizedStrings ls = LocalizedStrings.of(germanLocale, defaultString1).plus(englishLocale, defaultString2);
        assertThat(ls.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale).get()).isEqualTo(defaultString2);
    }

    @Test
    public void findTheFirstBestTranslation() throws Exception {
        final String actual = localizedStrings.get(asList(frenchLocale, englishLocale, germanLocale)).get();
        assertThat(actual).isEqualTo(defaultString2);
    }

    @Test
    public void returnPresentLocales() throws Exception {
        assertThat(localizedStrings.getLocales()).isEqualTo(new HashSet<>(asList(germanLocale, englishLocale)));
    }

    @Test
    public void implementToString() throws Exception {
        assertThat(localizedStrings.toString()).isEqualTo(format("LocalizedStrings(de -> %s, en -> %s)", defaultString1, defaultString2));
    }

    @Test
    public void implementEquals() throws Exception {
        assertThat(LocalizedStrings.of(germanLocale, defaultString1).plus(englishLocale, defaultString2).equals(localizedStrings)).isTrue();
        assertThat(LocalizedStrings.of(germanLocale, defaultString1).equals(localizedStrings)).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithCreator() throws Exception {
        LocalizedStrings.of(germanLocale, defaultString1, germanLocale, defaultString2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnDuplicateKeysWithPlus() throws Exception {
        LocalizedStrings.of(germanLocale, defaultString1).plus(germanLocale, defaultString2);
    }

    @Test
    public void jsonSerialize() throws Exception {
        assertThat(JsonUtils.newObjectMapper().writeValueAsString(dogFood)).isEqualTo(dogFoodJson);
    }

    @Test
    public void jsonDeserialize() throws Exception {
        assertThat(JsonUtils.newObjectMapper().readValue(dogFoodJson, LocalizedStrings.class)).isEqualTo(dogFood);
    }

    @Test
    public void slugify() throws Exception {
        final LocalizedStrings actual = LocalizedStrings.of(germanLocale, "Aa -A_", englishLocale, "dog food").slugified();
        final LocalizedStrings expected = LocalizedStrings.of(germanLocale, "aa-a_", englishLocale, "dog-food");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void stream() throws Exception {
        final LocalizedStrings upperCased = dogFood.stream()
                .map(e -> LocalizedStringsEntry.of(e.getLocale(), e.getValue().toUpperCase()))
                .collect(LocalizedStrings.streamCollector());
        assertThat(upperCased).isEqualTo(upperCasedDogFood);
    }

    @Test
    public void mapValue() throws Exception {
        final LocalizedStrings upperCased = dogFood.mapValue((locale, value) -> value.toUpperCase());
        assertThat(upperCased).isEqualTo(upperCasedDogFood);
    }
}
