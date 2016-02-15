package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Remove an attribute definition.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#addAttributeDefinition()}
 */
public final class RemoveAttributeDefinition extends UpdateActionImpl<ProductType> {
    private final String name;

    private RemoveAttributeDefinition(final String attributeName) {
        super("removeAttributeDefinition");
        this.name = attributeName;
    }

    public static RemoveAttributeDefinition of(final String attributeName) {
        return new RemoveAttributeDefinition(attributeName);
    }

    public String getName() {
        return name;
    }
}
