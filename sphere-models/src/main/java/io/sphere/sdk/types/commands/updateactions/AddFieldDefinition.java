package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.FieldDefinition;
import io.sphere.sdk.types.Type;

public class AddFieldDefinition extends UpdateActionImpl<Type> {
    private final FieldDefinition fieldDefinition;

    public static AddFieldDefinition of(final FieldDefinition fieldDefinition) {
        return new AddFieldDefinition(fieldDefinition);
    }

    private AddFieldDefinition(final FieldDefinition fieldDefinition) {
        super("addFieldDefinition");
        this.fieldDefinition = fieldDefinition;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }
}
