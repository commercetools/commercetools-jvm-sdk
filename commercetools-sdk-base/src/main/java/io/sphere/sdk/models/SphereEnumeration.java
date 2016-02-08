package io.sphere.sdk.models;

import java.util.Arrays;
import java.util.Optional;

public interface SphereEnumeration {
    String name();

    static <T extends Enum<T>> Optional<T> findBySphereName(final T[] values, final String sphereName) {
        return Arrays.stream(values)
                .filter(v -> SphereEnumerationUtils.toSphereName(v.name()).equals(sphereName))
                .findFirst();
    }

    default String toSphereName() {
        return SphereEnumerationUtils.toSphereName(name());
    }
}
