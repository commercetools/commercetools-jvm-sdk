package io.sphere.sdk.products.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public class ProductDeleteByIdCommand extends DeleteByIdCommandImpl<Product> {
    private ProductDeleteByIdCommand(final Versioned<Product> versioned) {
        super(versioned, ProductsEndpoint.ENDPOINT);
    }

    public static ProductDeleteByIdCommand of(final Versioned<Product> versioned) {
        return new ProductDeleteByIdCommand(versioned);
    }
}
