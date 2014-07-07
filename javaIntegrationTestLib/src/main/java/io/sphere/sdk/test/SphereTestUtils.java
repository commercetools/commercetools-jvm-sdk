package io.sphere.sdk.test;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.LocalizedString;

import java.util.Locale;

public final class SphereTestUtils {
    private SphereTestUtils() {
        //pure utility class
    }

    public static final CountryCode DE = CountryCode.DE;
    public static final CountryCode GB = CountryCode.GB;

    /**
     * Creates a LocalizedString for the {@code Locale.ENGLISH}.
     * @param value
     * @return
     */
    public static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
