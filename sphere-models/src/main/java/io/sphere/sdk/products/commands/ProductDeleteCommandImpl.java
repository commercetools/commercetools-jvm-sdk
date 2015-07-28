package io.sphere.sdk.products.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;

final class ProductDeleteCommandImpl extends ByIdDeleteCommandImpl<Product> {
    ProductDeleteCommandImpl(final Versioned<Product> versioned) {
        super(versioned, ProductEndpoint.ENDPOINT);
    }
}
