package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.requests.DeleteCommandImpl;

public class ProductDeleteCommand extends DeleteCommandImpl<Product> {
    public ProductDeleteCommand(final Versioned versionData) {
        super(versionData);
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "products";
    }

    @Override
    protected TypeReference<Product> typeReference() {
        return Product.typeReference();
    }
}
