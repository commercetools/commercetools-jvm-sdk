package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;

/**
 {@include.example io.sphere.sdk.products.ProductDeleteExample#delete()}
 */
public interface ProductDeleteCommand extends ByIdDeleteCommand<Product>, MetaModelExpansionDsl<Product, ProductDeleteCommand, ProductExpansionModel<Product>> {
    static ProductDeleteCommand of(final Versioned<Product> versioned) {
        return new ProductDeleteCommandImpl(versioned);
    }
}
