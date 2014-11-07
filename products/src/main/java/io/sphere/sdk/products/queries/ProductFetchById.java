package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.FetchByIdImpl;

public class ProductFetchById extends FetchByIdImpl<Product> {
    public ProductFetchById(final Identifiable<Product> identifiable) {
        super(identifiable, ProductsEndpoint.ENDPOINT);
    }
}
