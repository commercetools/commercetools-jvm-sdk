package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.Type;

import java.util.List;

/**
 * Changes the order of the fields.
 *
 * {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#changeFieldDefinitionOrder()}
 */
public final class ChangeFieldDefinitionOrder extends UpdateActionImpl<Type> {

    private final List<String> fieldNames;

    public static ChangeFieldDefinitionOrder of(final List<String> fieldNames) {
        return new ChangeFieldDefinitionOrder(fieldNames);
    }

    private ChangeFieldDefinitionOrder(final List<String> fieldNames) {
        super("changeFieldDefinitionOrder");
        this.fieldNames = fieldNames;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

}
