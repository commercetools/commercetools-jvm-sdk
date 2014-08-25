package io.sphere.sdk.utils;

import io.sphere.sdk.models.Builder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.sphere.sdk.utils.MapUtils.immutableCopyOf;

/**
 * A builder to create an immutable map.
 */
public class ImmutableMapBuilder<K, V> implements Builder<Map<K, V>> {
    private final Map<K, V> underlying = new HashMap<>();

    private ImmutableMapBuilder() {
    }

    public static <K, V> ImmutableMapBuilder<K, V> of() {
        return new ImmutableMapBuilder<>();
    }

    public ImmutableMapBuilder<K, V> put(final K key, final V value) {
        underlying.put(key, value);
        return this;
    }

    public ImmutableMapBuilder<K, V> putAll(final Map<K, V> map) {
        underlying.putAll(map);
        return this;
    }

    @Override
    public Map<K, V> build() {
        return immutableCopyOf(underlying);
    }
}
