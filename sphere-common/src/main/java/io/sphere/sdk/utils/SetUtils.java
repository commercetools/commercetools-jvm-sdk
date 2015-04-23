package io.sphere.sdk.utils;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public final class SetUtils {
    private SetUtils() {
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> asSet(final T ... params) {
        return new HashSet<>(asList(params));
    }

    public static <T> Set<T> setOf(final T element, final Set<T> set) {
        final Set<T> result = new HashSet<>(1 + set.size());
        result.add(element);
        result.addAll(set);
        return result;
    }

    public static <T> Set<T> setOf(final T element, final T[] array) {
        return setOf(element, asSet(array));
    }
}
