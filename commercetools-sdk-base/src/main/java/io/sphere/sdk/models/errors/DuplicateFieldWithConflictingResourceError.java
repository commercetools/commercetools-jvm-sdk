package io.sphere.sdk.models.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;

public final class DuplicateFieldWithConflictingResourceError extends SphereError {

    public static final String CODE = "DuplicateFieldWithConflictingResource";

    private final String field;
    private final String duplicateValue;
    private final Reference<Resource> conflictingResource;

    @JsonCreator
    private DuplicateFieldWithConflictingResourceError(final String message, final String field, final String duplicateValue, final Reference<Resource> conflictingResource) {
        super(CODE, message);
        this.field = field;
        this.duplicateValue = duplicateValue;
        this.conflictingResource = conflictingResource;
    }

    public static DuplicateFieldWithConflictingResourceError of(final String message, final String field, final String duplicateValue, final Reference<Resource> conflictingResource) {
        return new DuplicateFieldWithConflictingResourceError(message, field, duplicateValue, conflictingResource);
    }

    public String getField() {
        return field;
    }

    public String getDuplicateValue() {
        return duplicateValue;
    }

    public Reference<Resource> getConflictingResource() {
        return conflictingResource;
    }
}
