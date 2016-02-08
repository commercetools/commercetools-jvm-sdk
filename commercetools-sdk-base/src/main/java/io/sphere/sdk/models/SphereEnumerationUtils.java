package io.sphere.sdk.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

final class SphereEnumerationUtils {
    private SphereEnumerationUtils() {
    }

    static String toSphereName(final String uppercaseName) {
        return Arrays.stream(StringUtils.split(uppercaseName, '_'))
                .map(s -> s.toLowerCase())
                .map(StringUtils::capitalize)
                .collect(joining(""));
    }
}
