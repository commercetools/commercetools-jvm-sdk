package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.producttypes.ProductType;

/**
 * This action changes the label of a single enum value in an EnumType attribute definition. It can update an EnumType attribute definition or a Set of EnumType attribute definition. All products will be updated to the new label in an eventually consistent way.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changePlainEnumValueLabel()}
 */
public final class ChangePlainEnumValueLabel extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final EnumValue newValue;

    private ChangePlainEnumValueLabel(final String attributeName, final EnumValue newValue) {
        super("changePlainEnumValueLabel");
        this.attributeName = attributeName;
        this.newValue = newValue;
    }

    public static ChangePlainEnumValueLabel of(final String attributeName, final EnumValue newValue) {
        return new ChangePlainEnumValueLabel(attributeName, newValue);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public EnumValue getNewValue() {
        return newValue;
    }
}
