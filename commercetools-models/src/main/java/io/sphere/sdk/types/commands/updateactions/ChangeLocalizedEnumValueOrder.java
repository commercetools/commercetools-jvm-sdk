package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.types.Type;

import java.util.List;

/**
 * This action changes the order of localized enum values in a LocalizedEnumType field definition. It can update a LocalizedEnumType field definition or a Set of LocalizedEnumType field definition.
 *
 * {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#changeLocalizedEnumValueOrder()}
 */
public final class ChangeLocalizedEnumValueOrder extends UpdateActionImpl<Type> {

    private final String fieldName;
    private final List<String> keys;

    public static ChangeLocalizedEnumValueOrder of(final String fieldName, final List<String> keys) {
        return new ChangeLocalizedEnumValueOrder(fieldName, keys);
    }

    private ChangeLocalizedEnumValueOrder(final String fieldName, final List<String> keys) {
        super("changeLocalizedEnumValueOrder");
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
