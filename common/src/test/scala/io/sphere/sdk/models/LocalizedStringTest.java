package io.sphere.sdk.models;

import io.sphere.sdk.utils.JsonUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class LocalizedStringTest {
    private final Locale germanLocale = Locale.GERMAN;
    private final Locale englishLocale = Locale.ENGLISH;
    private final Locale frenchLocale = Locale.FRANCE;
    private final String defaultString1 = "foo";
    private final String defaultString2 = "bar";
    private final LocalizedString localizedString = LocalizedString.of(germanLocale, defaultString1, englishLocale, defaultString2);
    private final String dogFoodJson = "{\"de\":\"Hundefutter\",\"en\":\"dog food\"}";
    private final LocalizedString dogFood = LocalizedString.of(germanLocale, "Hundefutter", englishLocale, "dog food");

    @Test
    public void createFromOneValue() throws Exception {
        final LocalizedString ls = LocalizedString.of(germanLocale, defaultString1);
        assertThat(ls.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale)).isEqualTo(Optional.empty());
    }

    @Test
    public void createFromTwoValues() throws Exception {
        assertThat(localizedString.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(localizedString.get(englishLocale).get()).isEqualTo(defaultString2);
    }

    @Test
    public void createANewLocalizedStringByAddingALocale() throws Exception {
        final LocalizedString ls = LocalizedString.of(germanLocale, defaultString1).plus(englishLocale, defaultString2);
        assertThat(ls.get(germanLocale).get()).isEqualTo(defaultString1);
        assertThat(ls.get(englishLocale).get()).isEqualTo(defaultString2);
    }

    @Test
    public void findTheFirstBestTranslation() throws Exception {
        final String actual = localizedString.get(asList(frenchLocale, englishLocale, germanLocale)).get();
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
        assertThat(JsonUtils.newObjectMapper().writeValueAsString(dogFood)).isEqualTo(dogFoodJson);
    }

    @Test
    public void jsonDeserialize() throws Exception {
        assertThat(JsonUtils.newObjectMapper().readValue(dogFoodJson, LocalizedString.class)).isEqualTo(dogFood);
    }
}
