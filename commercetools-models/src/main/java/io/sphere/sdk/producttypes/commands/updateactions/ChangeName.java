package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Changes the  name of a product type.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<ProductType> {
    private final String name;

    private ChangeName(final String name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final String name) {
        return new ChangeName(name);
    }

    public String getName() {
        return name;
    }
}
