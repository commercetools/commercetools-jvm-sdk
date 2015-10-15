package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReferenceType extends FieldTypeBase {
    private final String referenceTypeId;

    @JsonCreator
    private ReferenceType(final String referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    @JsonIgnore
    public static ReferenceType of(final String referenceTypeId) {
        return new ReferenceType(referenceTypeId);
    }

    public String getReferenceTypeId() {
        return referenceTypeId;
    }
}
