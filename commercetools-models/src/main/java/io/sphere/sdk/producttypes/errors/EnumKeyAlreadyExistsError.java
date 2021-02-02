package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class EnumKeyAlreadyExistsError extends SphereError {

    public static final String CODE = "EnumKeyAlreadyExists";

    private final String conflictingEnumKey;
    private final String conflictingAttributeName;

    @JsonCreator
    private EnumKeyAlreadyExistsError(final String message, final String conflictingEnumKey, final String conflictingAttributeName) {
        super(CODE, message);
        this.conflictingEnumKey = conflictingEnumKey;
        this.conflictingAttributeName = conflictingAttributeName;
    }

    public static EnumKeyAlreadyExistsError of(final String message, final String conflictingEnumKey, final String conflictingAttributeName) {
        return new EnumKeyAlreadyExistsError(message, conflictingEnumKey, conflictingAttributeName);
    }

    public String getConflictingEnumKey() {
        return conflictingEnumKey;
    }

    public String getConflictingAttributeName() {
        return conflictingAttributeName;
    }
}
