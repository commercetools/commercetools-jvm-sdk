package io.sphere.sdk.types.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.Type;

/**
Changes the label.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.types.commands.TypeUpdateCommandIntegrationTest#changeLabel()}
 */
public final class ChangeFieldDefinitionLabel extends UpdateActionImpl<Type> {
    private final String fieldName;
    private final LocalizedString label;

    public static ChangeFieldDefinitionLabel of(final String fieldName, final LocalizedString label) {
        return new ChangeFieldDefinitionLabel(fieldName, label);
    }

    private ChangeFieldDefinitionLabel(final String fieldName, final LocalizedString label) {
        super("changeLabel");
        this.fieldName = fieldName;
        this.label = label;
    }

    public String getFieldName() {
        return fieldName;
    }

    public LocalizedString getLabel() {
        return label;
    }
}
