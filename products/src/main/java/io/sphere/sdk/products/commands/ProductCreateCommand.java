package io.sphere.sdk.products.commands;

import io.sphere.sdk.products.NewProduct;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.CreateCommandImpl;

public class ProductCreateCommand extends CreateCommandImpl<Product, NewProduct> {
    public ProductCreateCommand(final NewProduct body) {
        super(body, ProductsEndpoint.ENDPOINT);
    }
}
