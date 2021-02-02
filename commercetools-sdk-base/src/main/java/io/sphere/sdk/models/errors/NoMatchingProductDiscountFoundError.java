package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;


public final class NoMatchingProductDiscountFoundError extends SphereError {
    public static final String CODE = "NoMatchingProductDiscountFound";

    @JsonCreator
    private NoMatchingProductDiscountFoundError(final String message) {
        super(CODE, message);
    }

    public static NoMatchingProductDiscountFoundError of(final String message) {
        return new NoMatchingProductDiscountFoundError(message);
    }
}
