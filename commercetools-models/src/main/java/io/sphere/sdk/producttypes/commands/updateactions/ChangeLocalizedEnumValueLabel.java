package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.producttypes.ProductType;

/**
 * This action changes the label of a single enum value in a LocalizableEnumType attribute definition. It can update a LocalizableEnumType attribute definition or a Set of LocalizableEnumType attribute definition. All products will be updated to the new label in an eventually consistent way.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeLocalizedEnumValueLabel()}
 */
public final class ChangeLocalizedEnumValueLabel extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final LocalizedEnumValue newValue;

    private ChangeLocalizedEnumValueLabel(final String attributeName, final LocalizedEnumValue newValue) {
        super("changeLocalizedEnumValueLabel");
        this.attributeName = attributeName;
        this.newValue = newValue;
    }

    public static ChangeLocalizedEnumValueLabel of(final String attributeName, final LocalizedEnumValue newValue) {
        return new ChangeLocalizedEnumValueLabel(attributeName, newValue);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public LocalizedEnumValue getNewValue() {
        return newValue;
    }
}
