package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.FetchImpl;

public class FetchProductById extends FetchImpl<Product> {
    public FetchProductById(final Identifiable<Product> identifiable) {
        super(identifiable);
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
