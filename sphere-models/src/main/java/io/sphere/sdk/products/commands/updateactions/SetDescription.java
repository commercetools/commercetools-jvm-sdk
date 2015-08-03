package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

/**
 * Updates the description of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateActionImpl<Product> {
    private final LocalizedStrings description;

    private SetDescription(final LocalizedStrings description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedStrings description) {
        return new SetDescription(description);
    }

    public LocalizedStrings getDescription() {
        return description;
    }
}
