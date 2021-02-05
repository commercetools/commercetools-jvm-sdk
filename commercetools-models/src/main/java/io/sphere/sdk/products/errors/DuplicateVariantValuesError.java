package io.sphere.sdk.products.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;

import java.util.List;


public final class DuplicateVariantValuesError extends SphereError {

    public static final String CODE = "DuplicateVariantValues";

    private final List<String> variantValues;

    @JsonCreator
    private DuplicateVariantValuesError(final String message, final List<String> variantValues) {
        super(CODE, message);
        this.variantValues = variantValues;
    }

    public static DuplicateVariantValuesError of(final String message, final List<String> variantValues) {
        return new DuplicateVariantValuesError(message, variantValues);
    }

    public List<String> getVariantValues() {
        return variantValues;
    }
}
