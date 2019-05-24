package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.types.Type;

public final class ChangeLocalizedEnumValueLabel extends UpdateActionImpl<Type> {

    private final String fieldName;
    private final LocalizedEnumValue value;

    public static ChangeLocalizedEnumValueLabel of(final String fieldName, final LocalizedEnumValue value){
        return new ChangeLocalizedEnumValueLabel(fieldName, value);
    }
    
    private ChangeLocalizedEnumValueLabel(final String fieldName, final LocalizedEnumValue value) {
        super("changeLocalizedEnumValueLabel");
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
