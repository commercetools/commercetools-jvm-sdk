package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;

/**
 * Updates the slug of a product.
 *
 * {@include.example io.sphere.sdk.products.ProductCrudIntegrationTest#changeSlugUpdateAction()}
 */
public class ChangeSlug extends StageableProductUpdateAction {
    private final LocalizedStrings slug;

    private ChangeSlug(final LocalizedStrings slug, final boolean staged) {
        super("changeSlug", staged);
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedStrings slug, final boolean staged) {
        return new ChangeSlug(slug, staged);
    }

    public static ChangeSlug of(final LocalizedStrings slug) {
        return new ChangeSlug(slug, true);
    }

    public LocalizedStrings getSlug() {
        return slug;
    }
}
