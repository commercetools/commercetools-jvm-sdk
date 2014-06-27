package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.requests.DeleteCommandImpl;

public final class ProductTypeDeleteCommand extends DeleteCommandImpl<ProductType> {
    public ProductTypeDeleteCommand(Versioned versionData) {
        super(versionData);
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/product-types";
    }

    @Override
    protected TypeReference<ProductType> typeReference() {
        return ProductTypeImpl.typeReference();
    }
}
