package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class AttributeNameDoesNotExistError extends SphereError {

    public static final String CODE = "AttributeNameDoesNotExist";

    private final String invalidAttributeName;


    @JsonCreator
    private AttributeNameDoesNotExistError(final String message, final String invalidAttributeName) {
        super(CODE, message);
        this.invalidAttributeName = invalidAttributeName;

    }

    public static AttributeNameDoesNotExistError of(final String message, final String invalidAttributeName) {
        return new AttributeNameDoesNotExistError(message, invalidAttributeName);
    }

    public String getInvalidAttributeName() {
        return invalidAttributeName;
    }
}
