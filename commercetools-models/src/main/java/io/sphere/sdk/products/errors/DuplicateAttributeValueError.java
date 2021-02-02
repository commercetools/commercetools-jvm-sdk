package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.attributes.Attribute;


public final class DuplicateAttributeValueError extends SphereError {

    public static final String CODE = "DuplicateAttributeValue";

    private final Attribute attribute;

    @JsonCreator
    private DuplicateAttributeValueError(final String message, final Attribute attribute) {
        super(CODE, message);
        this.attribute = attribute;
    }

    public static DuplicateAttributeValueError of(final String message, final Attribute attribute) {
        return new DuplicateAttributeValueError(message, attribute);
    }

    public Attribute getAttribute() {
        return attribute;
    }
}
