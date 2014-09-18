package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.UpdateAction;

/**
 * Unpublishes a product, effectively deleting the current projection of the product, leaving only the staged projection. Consequently, when a product is unpublished, it will no longer be included in query or search results issued with staged=false, since such results only include current projections.
 *
 * {@include.example test.ProductCrudIntegrationTest#testPublishAndUnpublish()}
 */
public class Unpublish extends UpdateAction<Product> {
    private Unpublish() {
        super("unpublish");
    }

    public static Unpublish of() {
        return new Unpublish();
    }
}
