package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.FetchImpl;

public class ProductFetchById extends FetchImpl<Product> {
    public ProductFetchById(final Identifiable<Product> identifiable) {
        super(identifiable, ProductsEndpoint.ENDPOINT);
    }
}
