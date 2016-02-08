package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

/**
 * Updates the name of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<Product> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name);
    }

    public LocalizedString getName() {
        return name;
    }
}
