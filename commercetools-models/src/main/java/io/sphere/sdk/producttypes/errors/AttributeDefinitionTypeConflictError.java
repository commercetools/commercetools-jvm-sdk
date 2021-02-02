package io.sphere.sdk.producttypes.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.errors.SphereError;


public final class AttributeDefinitionTypeConflictError extends SphereError {

    public static final String CODE = "AttributeDefinitionTypeConflict";

    private final String conflictingProductTypeId;
    private final String conflictingProductTypeName;
    private final String conflictingAttributeName;

    @JsonCreator
    private AttributeDefinitionTypeConflictError(final String message, final String conflictingProductTypeId, final String conflictingProductTypeName, final String conflictingAttributeName) {
        super(CODE, message);
        this.conflictingProductTypeId = conflictingProductTypeId;
        this.conflictingProductTypeName = conflictingProductTypeName;
        this.conflictingAttributeName = conflictingAttributeName;
    }

    public static AttributeDefinitionTypeConflictError of(final String message, final String conflictingProductTypeId, final String conflictingProductTypeName, final String conflictingAttributeName) {
        return new AttributeDefinitionTypeConflictError(message, conflictingProductTypeId, conflictingProductTypeName, conflictingAttributeName);
    }

    public String getConflictingAttributeName() {
        return conflictingAttributeName;
    }

    public String getConflictingProductTypeName() {
        return conflictingProductTypeName;
    }

    public String getConflictingProductTypeId() {
        return conflictingProductTypeId;
    }
}
