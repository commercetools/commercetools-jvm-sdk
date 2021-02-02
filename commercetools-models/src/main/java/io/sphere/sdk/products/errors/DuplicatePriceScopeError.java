package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.Price;


public final class DuplicatePriceScopeError extends SphereError {

    public static final String CODE = "DuplicatePriceScope";

    private final Price[] conflictingPrices;

    @JsonCreator
    private DuplicatePriceScopeError(final String message, final Price[] conflictingPrices) {
        super(CODE, message);
        this.conflictingPrices = conflictingPrices;
    }

    public static DuplicatePriceScopeError of(final String message, final Price[] conflictingPrices) {
        return new DuplicatePriceScopeError(message, conflictingPrices);
    }

    public Price[] getConflictingPrices() {
        return conflictingPrices;
    }
}
