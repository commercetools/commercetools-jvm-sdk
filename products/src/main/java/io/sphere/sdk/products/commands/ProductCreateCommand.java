package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.products.NewProduct;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.CreateCommandImpl;

public class ProductCreateCommand extends CreateCommandImpl<Product, NewProduct> {
    public ProductCreateCommand(final NewProduct body) {
        super(body);
    }

    @Override
    protected String httpEndpoint() {
        return "/products";
    }

    @Override
    protected TypeReference<Product> typeReference() {
        return Product.typeReference();
    }
}
