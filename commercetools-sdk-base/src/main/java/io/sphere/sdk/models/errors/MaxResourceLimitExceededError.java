package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;

import java.lang.reflect.Type;


public final class MaxResourceLimitExceededError extends SphereError {

    public static final String CODE = "MaxResourceLimitExceeded";

    private final Reference<Type> exceededResource;

    @JsonCreator
    private MaxResourceLimitExceededError(final String message, final Reference<Type> exceededResource) {
        super(CODE, message);
        this.exceededResource = exceededResource;
    }

    public static MaxResourceLimitExceededError of(final String message, final Reference<Type> exceededResource) {
        return new MaxResourceLimitExceededError(message, exceededResource);
    }

    public Reference<Type> getExceededResource() {
        return exceededResource;
    }
}
