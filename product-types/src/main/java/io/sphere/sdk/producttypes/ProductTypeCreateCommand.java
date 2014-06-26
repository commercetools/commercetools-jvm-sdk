package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.CreateCommandImpl;

public class ProductTypeCreateCommand extends CreateCommandImpl<ProductType, ProductTypeImpl, NewProductType>{

    public ProductTypeCreateCommand(final NewProductType body) {
        super(body);
    }

    @Override
    protected String httpEndpoint() {
        return "/product-types";
    }

    @Override
    protected TypeReference<ProductTypeImpl> typeReference() {
        return ProductTypeImpl.typeReference();
    }
}
