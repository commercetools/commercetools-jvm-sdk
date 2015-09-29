package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.Type;

public class RemoveFieldDefinition extends UpdateActionImpl<Type> {
    private final String fieldName;

    public static RemoveFieldDefinition of(final String fieldName) {
        return new RemoveFieldDefinition(fieldName);
    }

    private RemoveFieldDefinition(final String fieldName) {
        super("removeFieldDefinition");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
