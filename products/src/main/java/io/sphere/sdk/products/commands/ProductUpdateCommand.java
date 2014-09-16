package io.sphere.sdk.products.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;

import java.util.List;

public class ProductUpdateCommand extends UpdateCommandDslImpl<Product> {
    public ProductUpdateCommand(final Versioned<Product> versioned, final List<UpdateAction<Product>> updateActions) {
        super(versioned, updateActions, ProductsEndpoint.ENDPOINT);
    }
}
