package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class ProductByIdFetch extends ByIdFetchImpl<Product> {
    private ProductByIdFetch(final String id) {
        super(id, ProductsEndpoint.ENDPOINT);
    }

    public static ProductByIdFetch of(final String id) {
        return new ProductByIdFetch(id);
    }

    public static ProductExpansionModel<Product> expansionPath() {
        return new ProductExpansionModel<>();
    }
}
