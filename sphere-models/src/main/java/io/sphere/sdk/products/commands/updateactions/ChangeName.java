package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

/**
 * Updates the name of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateActionImpl<Product> {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
