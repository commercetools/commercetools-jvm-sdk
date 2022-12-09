package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Lists;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.Price;

import java.util.List;

public final class DuplicatePriceScopeError extends SphereError {

    public static final String CODE = "DuplicatePriceScope";

    private final List<Price> conflictingPrices;

    @JsonCreator
    private DuplicatePriceScopeError(final String message, final List<Price> conflictingPrices, final Price conflictingPrice) {
        super(CODE, message);
        this.conflictingPrices = conflictingPrices != null ? conflictingPrices : Lists.newArrayList(conflictingPrice);
    }

    public static DuplicatePriceScopeError of(final String message, final List<Price> conflictingPrices) {
        return new DuplicatePriceScopeError(message, conflictingPrices, null);
    }

    public List<Price> getConflictingPrices() {
        return conflictingPrices;
    }
}
