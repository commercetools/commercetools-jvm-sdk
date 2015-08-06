package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeLabel()}
 */
public class ChangeAttributeDefinitionLabel extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final LocalizedString label;

    private ChangeAttributeDefinitionLabel(final String attributeName, final LocalizedString label) {
        super("changeLabel");
        this.attributeName = attributeName;
        this.label = label;
    }

    public static ChangeAttributeDefinitionLabel of(final String attributeName, final LocalizedString label) {
        return new ChangeAttributeDefinitionLabel(attributeName, label);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public LocalizedString getLabel() {
        return label;
    }
}
