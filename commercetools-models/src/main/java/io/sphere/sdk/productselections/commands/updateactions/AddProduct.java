package io.sphere.sdk.productselections.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.ProductSelection;

public final class AddProduct extends UpdateActionImpl<ProductSelection> {
    private final ResourceIdentifier<Product> product;

    private AddProduct(final ResourceIdentifier<Product> product) {
        super("addProduct");
        this.product = product;
    }

    public static AddProduct of(final ResourceIdentifier<Product> product) {
        return new AddProduct(product);
    }

    public ResourceIdentifier<Product> getProductResourceIdentifier() {
        return product;
    }
}
