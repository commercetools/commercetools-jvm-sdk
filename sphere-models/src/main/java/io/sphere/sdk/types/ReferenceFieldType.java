package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class ReferenceFieldType extends FieldTypeBase {
    private final String referenceTypeId;

    @JsonCreator
    private ReferenceFieldType(final String referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    @JsonIgnore
    public static ReferenceFieldType of(final String referenceTypeId) {
        return new ReferenceFieldType(referenceTypeId);
    }

    public String getReferenceTypeId() {
        return referenceTypeId;
    }
}
