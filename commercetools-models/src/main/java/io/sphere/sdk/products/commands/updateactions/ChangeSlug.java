package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Updates the slug of a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeSlug()}
 */
public final class ChangeSlug extends StagedProductUpdateActionImpl<Product> {
    private final LocalizedString slug;

    private ChangeSlug(final LocalizedString slug, @Nullable final Boolean staged) {
        super("changeSlug", staged);
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedString slug) {
        return of(slug, null);
    }

    public static ChangeSlug of(final LocalizedString slug, @Nullable final Boolean staged) {
        return new ChangeSlug(slug, staged);
    }

    public LocalizedString getSlug() {
        return slug;
    }

}
