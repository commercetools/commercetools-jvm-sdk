package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.requests.DeleteByIdCommandImpl;

public class ProductDeleteByIdCommand extends DeleteByIdCommandImpl<Product> {
    public ProductDeleteByIdCommand(final Versioned<Product> versioned) {
        super(versioned);
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/products";
    }

    @Override
    protected TypeReference<Product> typeReference() {
        return Product.typeReference();
    }
}
