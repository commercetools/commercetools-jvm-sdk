package io.sphere.sdk.http;

import java.util.*;

public interface NameValuePair {
    String getName();

    String getValue();

    static NameValuePair of(final String name, final String value) {
        return new NameValuePairImpl(name, value);
    }

    static List<NameValuePair> convertStringMapToList(final Map<String, String> data) {
        final List<NameValuePair> list = new ArrayList<>(data.size());
        data.forEach((key, value) -> list.add(NameValuePair.of(key, value)));
        return Collections.unmodifiableList(list);
    }

    static List<NameValuePair> convertStringListMapToList(final Map<String, List<String>> data) {
        final List<NameValuePair> list = new LinkedList<>();
        data.forEach((key, entriesList) -> {
            entriesList.forEach(value -> {
                list.add(NameValuePair.of(key, value));
            });
        });
        return Collections.unmodifiableList(list);
    }

    static Map<String, String> convertToStringMap(final List<NameValuePair> data) {
        final Map<String, String> map = new HashMap<>();
        data.forEach(pair -> map.put(pair.getName(), pair.getValue()));
        return Collections.unmodifiableMap(map);
    }

    static Map<String, List<String>> convertToStringListMap(final List<NameValuePair> data) {
        final Map<String, List<String>> map = new HashMap<>();
        data.forEach(pair -> {
            final List<String> headersForKey = map.computeIfAbsent(pair.getName(), name -> new LinkedList<>());
            headersForKey.add(pair.getValue());
        });
        return Collections.unmodifiableMap(map);
    }
}
