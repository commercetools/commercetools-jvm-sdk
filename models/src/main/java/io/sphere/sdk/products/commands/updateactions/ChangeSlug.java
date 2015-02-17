package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Updates the slug of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changeSlug()}
 */
public class ChangeSlug extends StageableProductUpdateAction {
    private final LocalizedStrings slug;

    private ChangeSlug(final LocalizedStrings slug, final ProductUpdateScope productUpdateScope) {
        super("changeSlug", productUpdateScope);
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedStrings slug, final ProductUpdateScope productUpdateScope) {
        return new ChangeSlug(slug, productUpdateScope);
    }

    public LocalizedStrings getSlug() {
        return slug;
    }
}
