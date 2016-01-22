package io.sphere.sdk.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.Normalizer;

public final class SphereStringUtils {
    //utility class
    private SphereStringUtils() {
    }

    public static String slugifyUnique(final String s) {
        return slugify(s + "-" + RandomStringUtils.randomNumeric(8));
    }

    public static String slugify(final String s) {
        //algorithm used in https://github.com/slugify/slugify/blob/master/core/src/main/java/com/github/slugify/Slugify.java
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^\\w+]", "-")
                .replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-")
                .replaceAll("^-", "")
                .replaceAll("-$", "")
                .toLowerCase();
    }
}
