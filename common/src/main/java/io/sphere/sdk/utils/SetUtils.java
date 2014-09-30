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
}
