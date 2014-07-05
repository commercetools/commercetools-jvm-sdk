package io.sphere.sdk.utils;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.function.Supplier;

public final class MapUtils {
    private MapUtils() {
    }

    public static <K, V> Optional<V> getOptional(final Map<K, V> map, final K key) {
        return Optional.fromNullable(map.get(key));
    }

    public static <K, V, E extends Throwable> V getOrThrow(final Map<K, V> map, final K key, Supplier<E> exceptionSupplier) throws E {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw exceptionSupplier.get();
        }
    }
}
