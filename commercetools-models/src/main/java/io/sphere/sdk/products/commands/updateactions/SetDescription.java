package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

/**
 * Updates the description of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<Product> {
    private final LocalizedString description;

    private SetDescription(final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedString description) {
        return new SetDescription(description);
    }

    public LocalizedString getDescription() {
        return description;
    }
}
