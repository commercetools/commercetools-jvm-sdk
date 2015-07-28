package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;

import java.util.List;

final class ProductUpdateCommandImpl extends UpdateCommandDslImpl<Product, ProductUpdateCommand> implements ProductUpdateCommand {
    ProductUpdateCommandImpl(final Versioned<Product> versioned, final List<? extends UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductEndpoint.ENDPOINT);
    }
}
