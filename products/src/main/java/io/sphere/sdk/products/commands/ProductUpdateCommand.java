package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.requests.UpdateAction;
import io.sphere.sdk.requests.UpdateCommandImpl;

import java.util.List;

public class ProductUpdateCommand extends UpdateCommandImpl<Product> {
    public ProductUpdateCommand(final Versioned versioned, final List<UpdateAction<Product>> updateActions) {
        super(versioned, updateActions);
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
