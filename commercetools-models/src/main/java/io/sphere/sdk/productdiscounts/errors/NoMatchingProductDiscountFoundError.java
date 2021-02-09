package io.sphere.sdk.productdiscounts.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.client.NotFoundException;


public final class NoMatchingProductDiscountFoundError extends NotFoundException {
    public static final String CODE = "NoMatchingProductDiscountFound";

    @JsonCreator
    private NoMatchingProductDiscountFoundError(final String message) {
        super(CODE);
    }

    public static NoMatchingProductDiscountFoundError of(final String message) {
        return new NoMatchingProductDiscountFoundError(message);
    }
}
