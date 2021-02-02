package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

public class ShippingMethodDoesNotMatchCartError extends SphereError {
    public static final String CODE = "DoesNotMatchCart";

    @JsonCreator
    private  ShippingMethodDoesNotMatchCartError(final String message) {
        super(CODE, message);
    }

    public static ShippingMethodDoesNotMatchCartError of(final String message) {
        return new ShippingMethodDoesNotMatchCartError(message);
    }
}
