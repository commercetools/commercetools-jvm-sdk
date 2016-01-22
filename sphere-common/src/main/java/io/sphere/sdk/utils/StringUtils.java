package io.sphere.sdk.utils;

@Deprecated
public final class StringUtils {
    //utility class
    private StringUtils() {
    }

    @Deprecated
    public static String slugifyUnique(final String s) {
        return SphereStringUtils.slugifyUnique(s);
    }

    @Deprecated
    public static String slugify(final String s) {
        return SphereStringUtils.slugify(s);
    }
}
