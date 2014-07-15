package io.sphere.sdk.utils;

import java.util.Optional;
import com.google.common.collect.Iterables;

public final class IterableUtils {
    private IterableUtils() {
    }

    public static <T> Optional<T> headOption(final Iterable<T> iterable) {
        return Optional.ofNullable(Iterables.getFirst(iterable, null));
    }
}
