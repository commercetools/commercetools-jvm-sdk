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
public final class AddProduct extends UpdateActionImpl<ProductSelection> {
    private final ResourceIdentifier<Product> productResourceIdentifier;

    private AddProduct(final ResourceIdentifier<Product> productResourceIdentifier) {
        super("addProduct");
        this.productResourceIdentifier = productResourceIdentifier;
    }

    public static AddProduct of(final ResourceIdentifier<Product> productResourceIdentifier) {
        return new AddProduct(productResourceIdentifier);
    }

    public ResourceIdentifier<Product> getProductResourceIdentifier() {
        return productResourceIdentifier;
    }
}
