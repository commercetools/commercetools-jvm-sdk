package io.sphere.sdk.productselections.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.ProductSelection;

/**
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productselections.commands.ProductSelectionUpdateCommandIntegrationTest#addProduct()}
 */
public final class RemoveProduct extends UpdateActionImpl<ProductSelection> {
    private final ResourceIdentifier<Product> productResourceIdentifier;

    private RemoveProduct(final ResourceIdentifier<Product> productResourceIdentifier) {
        super("removeProduct");
        this.productResourceIdentifier = productResourceIdentifier;
    }

    public static RemoveProduct of(final ResourceIdentifier<Product> productResourceIdentifier) {
        return new RemoveProduct(productResourceIdentifier);
    }

    public ResourceIdentifier<Product> getProductResourceIdentifier() {
        return productResourceIdentifier;
    }
}
