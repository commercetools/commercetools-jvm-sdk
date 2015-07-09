package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.producttypes.ProductType;

/**
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeLabel()}
 */
public class ChangeAttributeDefinitionLabel extends UpdateAction<ProductType> {
    private final String attributeName;
    private final LocalizedStrings label;

    private ChangeAttributeDefinitionLabel(final String attributeName, final LocalizedStrings label) {
        super("changeLabel");
        this.attributeName = attributeName;
        this.label = label;
    }

    public static ChangeAttributeDefinitionLabel of(final String attributeName, final LocalizedStrings label) {
        return new ChangeAttributeDefinitionLabel(attributeName, label);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public LocalizedStrings getLabel() {
        return label;
    }
}
