package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.FetchByIdImpl;

public class ProductFetchById extends FetchByIdImpl<Product> {
    private ProductFetchById(final String id) {
        super(id, ProductsEndpoint.ENDPOINT);
    }

    public static ProductFetchById of(final String id) {
        return new ProductFetchById(id);
    }
}
