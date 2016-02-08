package io.sphere.sdk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @deprecated This is an internal class.
 */
@Deprecated
public final class IterableUtils {
    private IterableUtils() {
    }

    public static boolean isEmpty(final Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }

    public static <T> Iterable<T> requireNonEmpty(final Iterable<T> iterable) {
        if (isEmpty(iterable)) {
            throw new IllegalArgumentException("iterable must not be empty.");
        }
        return iterable;
    }

    public static <T> List<T> toList(final Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (final T item : iterable) {
            list.add(item);
        }
        return list;
    }

    public static <T> Stream<T> toStream(final Iterable<T> iterable) {
        return toList(iterable).stream();
    }

}
