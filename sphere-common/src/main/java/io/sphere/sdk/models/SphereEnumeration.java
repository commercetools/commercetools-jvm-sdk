package io.sphere.sdk.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public interface SphereEnumeration {
    String name();

    static <T extends Enum<T>> T find(final T[] values, final String value) {
        return Arrays.stream(values)
                .filter(v -> toSphereName(v.name()).equals(value))
                .findFirst().get();
    }

    public static String toSphereName(final String uppercaseName) {
        return Arrays.stream(StringUtils.split(uppercaseName, '_'))
                .map(s -> s.toLowerCase())
                .map(StringUtils::capitalize)
                .collect(joining(""));
    }
}
