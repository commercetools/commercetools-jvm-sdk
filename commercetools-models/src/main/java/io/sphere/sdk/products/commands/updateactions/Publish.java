package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.PublishScope;

import javax.annotation.Nullable;

/**
 * Publishes a product, which causes the staged projection of the product to override the current projection. If the product is published for the first time, the current projection is created.
 * <p>
 * {@doc.gen intro}
 * <p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#publish()}
 *
 * @see io.sphere.sdk.products.messages.ProductPublishedMessage
 */
public final class Publish extends UpdateActionImpl<Product> {

    @Nullable
    final private PublishScope scope;

    private Publish(@Nullable final PublishScope scope) {
        super("publish");
        this.scope = scope;
    }

    public static Publish of() {
        return ofScope(null);
    }

    public static Publish ofScope(@Nullable final PublishScope scope) {
        return new Publish(scope);
    }

    @Nullable
    public PublishScope getScope() {
        return scope;
    }
}
