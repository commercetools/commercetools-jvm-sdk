package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.types.LocalizedEnumFieldType;
import io.sphere.sdk.types.Type;

/**
 Adds an a new {@link LocalizedEnumValue} value to an {@link LocalizedEnumFieldType} field.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#addLocalizedEnumValue()}

 @see LocalizedEnumFieldType#getValues()
 */
public final class AddLocalizedEnumValue extends UpdateActionImpl<Type> {
    private final String fieldName;
    private final LocalizedEnumValue value;

    public static AddLocalizedEnumValue of(final String fieldName, final LocalizedEnumValue value) {
        return new AddLocalizedEnumValue(fieldName, value);
    }

    private AddLocalizedEnumValue(final String fieldName, final LocalizedEnumValue value) {
        super("addLocalizedEnumValue");
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public LocalizedEnumValue getValue() {
        return value;
    }
}
