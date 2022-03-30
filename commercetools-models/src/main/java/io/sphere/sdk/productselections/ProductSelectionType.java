package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * ProductSelectionType.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 */
public enum ProductSelectionType {
    INDIVIDUAL("individual");

    private final String jsonValue;

    private ProductSelectionType(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }
}
