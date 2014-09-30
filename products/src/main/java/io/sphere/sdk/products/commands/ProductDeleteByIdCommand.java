package io.sphere.sdk.products.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public class ProductDeleteByIdCommand extends DeleteByIdCommandImpl<Product> {
    public ProductDeleteByIdCommand(final Versioned<Product> versioned) {
        super(versioned, ProductsEndpoint.ENDPOINT);
    }
}
