package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.types.Type;

public final class ChangeInputHint extends UpdateActionImpl<Type> {

    private final String fieldName;
    private final TextInputHint inputHint;

    public static ChangeInputHint of(final String fieldName, final TextInputHint inputHint){
        return new ChangeInputHint(fieldName, inputHint);
    }
    
    private ChangeInputHint(final String fieldName, final TextInputHint inputHint) {
        super("changeInputHint");
        this.fieldName = fieldName;
        this.inputHint = inputHint;
    }

    public String getFieldName() {
        return fieldName;
    }

    public TextInputHint getInputHint() {
        return inputHint;
    }
}
