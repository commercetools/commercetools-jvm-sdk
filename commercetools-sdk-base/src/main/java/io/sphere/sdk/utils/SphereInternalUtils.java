package io.sphere.sdk.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.Normalizer;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public final class SphereInternalUtils {

    private static final int MAX_SLUG_LENGTH = 256;

    private SphereInternalUtils() {
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

    public static <T> List<T> listOf(List<? extends T> first, List<? extends T> second) {
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


    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> asSet(final T ... params) {
        return new LinkedHashSet<>(asList(params));
    }

    public static <T> Set<T> setOf(final T element, final Set<T> set) {
        final Set<T> result = new HashSet<>(1 + set.size());
        result.add(element);
        result.addAll(set);
        return result;
    }

    public static <T> Set<T> setOf(final Set<T> first, final Set<T> second) {
        final Set<T> result = new HashSet<>(first.size() + second.size());
        result.addAll(first);
        result.addAll(second);
        return result;
    }

    public static <T> Set<T> setOf(final T element, final T[] array) {
        return setOf(element, asSet(array));
    }

    public static String slugifyUnique(final String s) {
        final String postFix = "-" + RandomStringUtils.randomNumeric(8);
        final String intermediate = slugify(s);
        final String result = intermediate.substring(0, Math.min(intermediate.length(), MAX_SLUG_LENGTH - postFix.length())) + postFix;
        return result;
    }

    public static String slugify(final String s) {
        //algorithm used in https://github.com/slugify/slugify/blob/master/core/src/main/java/com/github/slugify/Slugify.java
        final String intermediateResult = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^-_a-zA-Z0-9]", "-")
                .replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-")
                .replaceAll("^-", "")
                .replaceAll("-$", "")
                .toLowerCase();
        return intermediateResult.substring(0, Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
    }

    public static <K, V, E extends Throwable> V getOrThrow(final Map<K, V> map, final K key, Supplier<E> exceptionSupplier) throws E {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw exceptionSupplier.get();
        }
    }

    public static <K, V> Map<K, V> immutableCopyOf(final Map<K, V> map) {
        return Collections.unmodifiableMap(copyOf(map));
    }

    public static <K, V> Map<K, V> copyOf(final Map<K, V> map) {
        final Map<K, V> copy = new LinkedHashMap<>();
        copy.putAll(map);
        return copy;
    }

    public static <K, V> Map<K, V> mapOf(final K key, final V value) {
        final Map<K, V> result = new LinkedHashMap<>();
        result.put(key, value);
        return result;
    }

    public static <K, V> Map<K, V> mapOf(final K key1, final V value1, final K key2, final V value2) {
        if (key1.equals(key2)) {
            throw new IllegalArgumentException(format("Duplicate keys (%s) for map creation.", key1));
        }
        final Map<K, V> result = new LinkedHashMap<>();
        result.put(key1, value1);
        result.put(key2, value2);
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
