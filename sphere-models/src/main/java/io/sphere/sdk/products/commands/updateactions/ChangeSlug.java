package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.Product;

/**
 * Updates the slug of a product.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#changeSlug()}
 */
public class ChangeSlug extends UpdateAction<Product> {
    private final LocalizedStrings slug;

    private ChangeSlug(final LocalizedStrings slug) {
        super("changeSlug");
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedStrings slug) {
        return new ChangeSlug(slug);
    }

    public LocalizedStrings getSlug() {
        return slug;
    }
}
