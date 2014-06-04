package io.sphere.sdk.utils;

import com.google.common.base.Optional;

import java.util.Map;

public final class MapUtils {
    private MapUtils() {
    }

    public static <K, V> Optional<V> getOptional(final Map<K, V> map, final K key) {
        return Optional.fromNullable(map.get(key));
    }
}
