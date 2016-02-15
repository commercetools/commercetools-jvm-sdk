package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

/**
 * Unpublishes a product, effectively deleting the current projection of the product, leaving only the staged projection. Consequently, when a product is unpublished, it will no longer be included in query or search results issued with staged=false, since such results only include current projections.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#publish()}
 *
 * @see io.sphere.sdk.products.messages.ProductUnpublishedMessage
 */
public final class Unpublish extends UpdateActionImpl<Product> {
    private Unpublish() {
        super("unpublish");
    }

    public static Unpublish of() {
        return new Unpublish();
    }
}
