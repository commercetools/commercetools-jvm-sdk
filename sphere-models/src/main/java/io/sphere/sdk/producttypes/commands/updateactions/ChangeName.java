package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.producttypes.ProductType;

/**
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateAction<ProductType> {
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
