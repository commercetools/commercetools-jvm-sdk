package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.Price;

import java.util.List;

public final class DuplicatePriceScopeError extends SphereError {

    public static final String CODE = "DuplicatePriceScope";

    private final List<Price> conflictingPrices;

    @JsonCreator
    private DuplicatePriceScopeError(final String message, final List<Price> conflictingPrices) {
        super(CODE, message);
        this.conflictingPrices = conflictingPrices;
    }

    public static DuplicatePriceScopeError of(final String message, final List<Price> conflictingPrices) {
        return new DuplicatePriceScopeError(message, conflictingPrices);
    }

    public List<Price> getConflictingPrices() {
        return conflictingPrices;
    }
}
