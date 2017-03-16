package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Updates the description of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends StagedProductUpdateActionImpl<Product> {
    private final LocalizedString description;

    private SetDescription(final LocalizedString description, @Nullable final Boolean staged) {
        super("setDescription", staged);
        this.description = description;
    }

    public static SetDescription of(final LocalizedString description) {
        return of(description, null);
    }

    public static SetDescription of(final LocalizedString description, @Nullable final Boolean staged) {
        return new SetDescription(description, staged);
    }

    public LocalizedString getDescription() {
        return description;
    }

}
