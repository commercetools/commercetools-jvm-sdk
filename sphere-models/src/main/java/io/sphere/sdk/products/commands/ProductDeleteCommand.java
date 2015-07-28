package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public interface ProductDeleteCommand extends ByIdDeleteCommand<Product> {
    static DeleteCommand<Product> of(final Versioned<Product> versioned) {
        return new ProductDeleteCommandImpl(versioned);
    }
}
