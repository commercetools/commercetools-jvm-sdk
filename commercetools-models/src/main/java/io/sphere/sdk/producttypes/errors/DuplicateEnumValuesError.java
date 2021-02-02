package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class DuplicateEnumValuesError extends SphereError {

    public static final String CODE = "DuplicateEnumValues";

    private final String[] duplicates;

    @JsonCreator
    private DuplicateEnumValuesError(final String message, final String[] duplicates) {
        super(CODE, message);
        this.duplicates = duplicates;
    }

    public static DuplicateEnumValuesError of(final String message, final String[] duplicates) {
        return new DuplicateEnumValuesError(message, duplicates);
    }

    public String[] getDuplicates() {
        return duplicates;
    }
}
