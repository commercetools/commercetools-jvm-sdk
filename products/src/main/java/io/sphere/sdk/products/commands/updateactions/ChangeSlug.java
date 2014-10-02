package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;

/**
 * Updates the slug of a product.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#changeSlugUpdateAction()}
 */
public class ChangeSlug extends StageableProductUpdateAction {
    private final LocalizedString slug;

    private ChangeSlug(final LocalizedString slug, final boolean staged) {
        super("changeSlug", staged);
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedString slug, final boolean staged) {
        return new ChangeSlug(slug, staged);
    }

    public static ChangeSlug of(final LocalizedString slug) {
        return new ChangeSlug(slug, true);
    }

    public LocalizedString getSlug() {
        return slug;
    }
}
