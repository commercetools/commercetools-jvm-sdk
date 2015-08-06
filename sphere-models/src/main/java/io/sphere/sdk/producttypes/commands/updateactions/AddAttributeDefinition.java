package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#addAttributeDefinition()}
 */
public class AddAttributeDefinition extends UpdateActionImpl<ProductType> {
    private final AttributeDefinition attribute;

    private AddAttributeDefinition(final AttributeDefinition attributeDefinition) {
        super("addAttributeDefinition");
        this.attribute = attributeDefinition;
    }

    public static AddAttributeDefinition of(final AttributeDefinition attributeDefinition) {
        return new AddAttributeDefinition(attributeDefinition);
    }

    public AttributeDefinition getAttribute() {
        return attribute;
    }
}
