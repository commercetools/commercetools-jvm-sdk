package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandImpl;

import java.util.List;

public class ProductUpdateCommand extends UpdateCommandImpl<Product> {
    public ProductUpdateCommand(final Versioned<Product> versioned, final List<UpdateAction<Product>> updateActions) {
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
