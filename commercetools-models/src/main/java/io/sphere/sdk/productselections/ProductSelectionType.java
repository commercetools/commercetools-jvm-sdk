package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * InventoryMode.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 */
public enum ProductSelectionType implements SphereEnumeration {
    INDIVIDUAL;

    public static ProductSelectionType defaultValue() {
        return ProductSelectionType.INDIVIDUAL;
    }

    @JsonCreator
    public static ProductSelectionType ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
