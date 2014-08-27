package io.sphere.sdk.categories;

import java.util.*;

final class Multimap<K,V> {
    private final Map<K, List<V>> underlyingMap = new HashMap<>();

    public void put(final K key, final V value) {
        final List<V> entriesForKey = underlyingMap.getOrDefault(key, new LinkedList<>());
        entriesForKey.add(value);
        underlyingMap.put(key, entriesForKey);
    }

    public Collection<V> get(final K key) {
        return underlyingMap.get(key);
    }
}
