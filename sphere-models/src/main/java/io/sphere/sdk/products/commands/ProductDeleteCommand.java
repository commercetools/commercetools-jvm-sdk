package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public class ProductDeleteCommand extends ByIdDeleteCommandImpl<Product> {
    private ProductDeleteCommand(final Versioned<Product> versioned) {
        super(versioned, ProductEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Product> of(final Versioned<Product> versioned) {
        return new ProductDeleteCommand(versioned);
    }
}
