package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.Type;

import java.util.List;

/**
 * This action changes the order of enum values in an EnumType field definition. It can update an EnumType field definition or a Set of EnumType field definition.
 *
 * {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#changeEnumValueOrder()}
 */
public final class ChangeEnumValueOrder extends UpdateActionImpl<Type> {

    private final String fieldName;
    private final List<String> keys;

    public static ChangeEnumValueOrder of(final String fieldName, final List<String> keys) {
        return new ChangeEnumValueOrder(fieldName, keys);
    }

    private ChangeEnumValueOrder(final String fieldName, final List<String> keys) {
        super("changeEnumValueOrder");
        this.fieldName = fieldName;
        this.keys = keys;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getKeys() {
        return keys;
    }
}
