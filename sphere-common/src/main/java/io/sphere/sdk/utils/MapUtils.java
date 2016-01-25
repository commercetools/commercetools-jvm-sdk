package io.sphere.sdk.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.format;

/**
 * @deprecated This is an internal class.
 */
@Deprecated
public final class MapUtils {
    private MapUtils() {
    }

    public static <K, V, E extends Throwable> V getOrThrow(final Map<K, V> map, final K key, Supplier<E> exceptionSupplier) throws E {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw exceptionSupplier.get();
        }
    }

    public static  <K, V> Map<K, V> immutableCopyOf(final Map<K, V> map) {
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
}
