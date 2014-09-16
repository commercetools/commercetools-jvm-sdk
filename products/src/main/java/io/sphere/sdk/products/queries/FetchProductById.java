package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.FetchImpl;

public class FetchProductById extends FetchImpl<Product> {
    public FetchProductById(final Identifiable<Product> identifiable) {
        super(identifiable, ProductsEndpoint.ENDPOINT);
    }
}
