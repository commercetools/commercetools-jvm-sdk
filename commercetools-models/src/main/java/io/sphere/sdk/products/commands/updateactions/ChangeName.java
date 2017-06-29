package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Updates the name of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends StagedProductUpdateActionImpl<Product> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name, @Nullable final Boolean staged) {
        super("changeName", staged);
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name) {
        return of(name, null);
    }

    public static ChangeName of(final LocalizedString name, @Nullable final Boolean staged) {
        return new ChangeName(name, staged);
    }

    public LocalizedString getName() {
        return name;
    }
}
