package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Adds an attribute definition to a product type.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#addAttributeDefinition()}
 *
 * @see ProductType#getAttributes()
 * @see io.sphere.sdk.products.ProductVariant#getAttributes()
 */
public final class AddAttributeDefinition extends UpdateActionImpl<ProductType> {
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
