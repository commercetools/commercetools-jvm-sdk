package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class EnumKeyDoesNotExistError extends SphereError {

    public static final String CODE = "EnumKeyDoesNotExist";

    private final String conflictingEnumKey;
    private final String conflictingAttributeName;

    @JsonCreator
    private EnumKeyDoesNotExistError(final String message, final String conflictingEnumKey, final String conflictingAttributeName) {
        super(CODE, message);
        this.conflictingEnumKey = conflictingEnumKey;
        this.conflictingAttributeName = conflictingAttributeName;
    }

    public static EnumKeyDoesNotExistError of(final String message, final String conflictingEnumKey, final String conflictingAttributeName) {
        return new EnumKeyDoesNotExistError(message, conflictingEnumKey, conflictingAttributeName);
    }

    public String getConflictingEnumKey() {
        return conflictingEnumKey;
    }

    public String getConflictingAttributeName() {
        return conflictingAttributeName;
    }
}
