package io.sphere.sdk.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public final class ListUtils {
    private ListUtils() {
    }

    public static <T> String join(final List<T> list) {
        return list.stream().map(i -> i.toString()).collect(joining(", "));
    }

    public static <T> List<T> immutableCopyOf(final List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asImmutableList(final T ... elements) {
        return Collections.unmodifiableList(asList(elements));
    }

    @SuppressWarnings("varargs")
    public static <T> List<T> listOf(final T element, final T[] array) {
        final List<T> result = new ArrayList<>(1 + array.length);
        result.add(element);
        result.addAll(asList(array));
        return result;
    }

    public static <T> List<T> listOf(final List<T> elements, final T element) {
        final List<T> result = new ArrayList<>(1 + elements.size());
        result.addAll(elements);
        result.add(element);
        return result;
    }

    public static <T> List<T> listOf(List<T> first, List<T> second) {
        final List<T> result = new ArrayList<>(second.size() + first.size());
        result.addAll(first);
        result.addAll(second);
        return result;
    }

    public static <T> List<T> reverse(final List<T> list) {
        final ArrayList<T> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
