package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.producttypes.ProductType;

/**
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#addAttributeDefinition()}
 */
public class RemoveAttributeDefinition extends UpdateAction<ProductType> {
    private final String name;

    private RemoveAttributeDefinition(final String name) {
        super("removeAttributeDefinition");
        this.name = name;
    }

    public static RemoveAttributeDefinition of(final String name) {
        return new RemoveAttributeDefinition(name);
    }

    public String getName() {
        return name;
    }
}
