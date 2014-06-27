package io.sphere.sdk.test;

import io.sphere.sdk.models.LocalizedString;

import java.util.Locale;

public final class SphereTestUtils {
    private SphereTestUtils() {
        //pure utility class
    }

    /**
     * Creates a LocalizedString for the {@code Locale.ENGLISH}.
     * @param value
     * @return
     */
    public static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
