package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.attributes.Attribute;


public final class DuplicateAttributeValuesError extends SphereError {

    public static final String CODE = "DuplicateAttributeValues";

    private final Attribute[] attributes;

    @JsonCreator
    private DuplicateAttributeValuesError(final String message, final Attribute[] attributes) {
        super(CODE, message);
        this.attributes = attributes;
    }

    public static DuplicateAttributeValuesError of(final String message, final Attribute[] attributes) {
        return new DuplicateAttributeValuesError(message, attributes);
    }

    public Attribute[] getAttributes() {
        return attributes;
    }
}
