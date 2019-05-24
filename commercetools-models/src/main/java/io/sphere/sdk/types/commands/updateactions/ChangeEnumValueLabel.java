package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.types.Type;

public final class ChangeEnumValueLabel extends UpdateActionImpl<Type> {

    private final String fieldName;
    private final EnumValue value;

    public static ChangeEnumValueLabel of(final String fieldName, final EnumValue value){
        return new ChangeEnumValueLabel(fieldName, value);
    }
    
    private ChangeEnumValueLabel(final String fieldName, final EnumValue value) {
        super("changeEnumValueLabel");
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public EnumValue getValue() {
        return value;
    }
}
