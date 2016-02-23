package io.sphere.sdk.models;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of enum constants in the commercetools platform.
 *
 * <p>Hints for importing {@link SphereEnumeration}s:</p>
 *
 * {@include.example io.sphere.sdk.products.attributes.AttributeConstraintTest#demoImportSphereValue()}
 * {@include.example io.sphere.sdk.products.attributes.AttributeConstraintTest#demoImportUpperCase()}
 *
 * <p>Hints for exporting {@link SphereEnumeration}s:</p>
 *
 * {@include.example io.sphere.sdk.products.attributes.AttributeConstraintTest#demoExportSphereValue()}
 * {@include.example io.sphere.sdk.products.attributes.AttributeConstraintTest#demoExportUpperCase()}
 */
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
