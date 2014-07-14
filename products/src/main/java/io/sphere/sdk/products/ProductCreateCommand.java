package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.CreateCommandImpl;

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
