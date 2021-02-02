package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;
import io.sphere.sdk.products.Price;

import java.lang.reflect.Array;


public final class DuplicateVariantValuesError extends SphereError {

    public static final String CODE = "DuplicateVariantValues";

    private final String[] variantValues;

    @JsonCreator
    private DuplicateVariantValuesError(final String message, final String[] variantValues) {
        super(CODE, message);
        this.variantValues = variantValues;
    }

    public static DuplicateVariantValuesError of(final String message, final String[] variantValues) {
        return new DuplicateVariantValuesError(message, variantValues);
    }

    public String[] getVariantValues() {
        return variantValues;
    }
}
