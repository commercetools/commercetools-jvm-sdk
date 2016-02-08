package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;

final class ProductDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Product, ProductDeleteCommand, ProductExpansionModel<Product>> implements ProductDeleteCommand {
    ProductDeleteCommandImpl(final Versioned<Product> versioned) {
        super(versioned, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductDeleteCommandImpl::new);
    }

    ProductDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Product, ProductDeleteCommand, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}
