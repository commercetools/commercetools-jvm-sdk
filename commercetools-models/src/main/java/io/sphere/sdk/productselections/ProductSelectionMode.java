package io.sphere.sdk.productselections;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductSelectionMode {
    INDIVIDUAL("Individual"),

    INDIVIDUAL_EXCLUSION("IndividualExclusion");

    private final String jsonValue;

    private ProductSelectionMode(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }

}
