package io.sphere.sdk.common;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

public final class IterableUtils {
    private IterableUtils() {
    }

    public static <T> Optional<T> headOption(final Iterable<T> iterable) {
        return Optional.fromNullable(Iterables.getFirst(iterable, null));
    }
}
