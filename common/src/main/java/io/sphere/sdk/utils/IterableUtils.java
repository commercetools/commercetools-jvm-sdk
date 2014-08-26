package io.sphere.sdk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

public final class IterableUtils {
    private IterableUtils() {
    }

    public static <T> Optional<T> headOption(final Iterable<T> iterable) {
        Optional<T> result = Optional.empty();
        if (!isEmpty(iterable)) {
            result = Optional.of(iterable.iterator().next());
        }
        return result;
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
        List<T> list = new ArrayList<T>();
        for (final T item : iterable) {
            list.add(item);
        }
        return list;
    }

    public static <T> Stream<T> toStream(final Iterable<T> iterable) {
        return toList(iterable).stream();
    }

    public static <T> String toString(final Iterable<T> iterable) {
        final StringJoiner joiner = new StringJoiner(", ");
        toList(iterable).forEach(item -> joiner.add(item.toString()));
        return "[" + joiner.toString() + "]";
    }
}
