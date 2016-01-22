package io.sphere.sdk.utils;

/**
 * @deprecated This is an internal class.
 */
@Deprecated
public final class StringUtils {
    //utility class
    private StringUtils() {
    }

    @Deprecated
    public static String slugifyUnique(final String s) {
        return SphereInternalUtils.slugifyUnique(s);
    }

    @Deprecated
    public static String slugify(final String s) {
        return SphereInternalUtils.slugify(s);
    }
}
