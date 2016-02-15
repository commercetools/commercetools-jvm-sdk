package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

/**
 * Publishes a product, which causes the staged projection of the product to override the current projection. If the product is published for the first time, the current projection is created.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#publish()}
 *
 * @see io.sphere.sdk.products.messages.ProductPublishedMessage
 */
public final class Publish extends UpdateActionImpl<Product> {
    private Publish() {
        super("publish");
    }

    public static Publish of() {
        return new Publish();
    }
}
