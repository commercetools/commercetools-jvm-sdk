package io.sphere.sdk.utils;

import java.util.Set;

/**
 * @deprecated This is an internal class.
 */
@Deprecated
public final class SetUtils {
    private SetUtils() {
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> asSet(final T ... params) {
        return SphereInternalUtils.asSet(params);
    }

    public static <T> Set<T> setOf(final T element, final Set<T> set) {
        return SphereInternalUtils.setOf(element, set);
    }

    public static <T> Set<T> setOf(final T element, final T[] array) {
        return SphereInternalUtils.setOf(element, array);
    }
}
