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
public final class ChangeSlug extends StagedBase<Product> {
    private final LocalizedString slug;

    private ChangeSlug(final LocalizedString slug, final boolean staged) {
        super("changeSlug", staged);
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedString slug) {
        return of(slug, true);
    }

    public static ChangeSlug of(final LocalizedString slug, @Nullable final boolean staged) {
        return new ChangeSlug(slug, staged);
    }

    public LocalizedString getSlug() {
        return slug;
    }

}
