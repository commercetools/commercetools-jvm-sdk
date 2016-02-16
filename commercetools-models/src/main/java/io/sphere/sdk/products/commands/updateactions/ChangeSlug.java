package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

/**
 * Updates the slug of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeSlug()}
 */
public final class ChangeSlug extends UpdateActionImpl<Product> {
    private final LocalizedString slug;

    private ChangeSlug(final LocalizedString slug) {
        super("changeSlug");
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedString slug) {
        return new ChangeSlug(slug);
    }

    public LocalizedString getSlug() {
        return slug;
    }
}
