package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;

import java.util.List;

final class ProductUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Product, ProductUpdateCommand, ProductExpansionModel<Product>> implements ProductUpdateCommand {
    ProductUpdateCommandImpl(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductEndpoint.ENDPOINT, ProductUpdateCommandImpl::new, ProductExpansionModel.of());
    }

    ProductUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Product, ProductUpdateCommand, ProductExpansionModel<Product>> builder) {
        super(builder);
    }
}
