package io.sphere.sdk.test;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithLocalizedSlug;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.Locale;
import java.util.Random;

public final class SphereTestUtils {
    private static final Random random = new Random();

    private SphereTestUtils() {
        //pure utility class
    }

    public static final CountryCode DE = CountryCode.DE;
    public static final CountryCode GB = CountryCode.GB;

    /**
     * Creates a LocalizedString for the {@code Locale.ENGLISH}.
     * @param value the value of the english translation
     * @return localized string with value
     */
    public static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }

    public static String englishSlugOf(final WithLocalizedSlug model) {
        return model.getSlug().get(Locale.ENGLISH).get();
    }

    public static <T> T firstOf(final PagedQueryResult<T> result) {
        return result.head().get();
    }

    public static LocalizedString randomSlug() {
        return LocalizedString.of(Locale.ENGLISH, "random-slug-" + random.nextInt());
    }
}
