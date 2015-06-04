package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class ProductByIdFetch extends ByIdFetchImpl<Product> {
    private ProductByIdFetch(final String id) {
        super(id, ProductsEndpoint.ENDPOINT);
    }

    public static ProductByIdFetch of(final String id) {
        return new ProductByIdFetch(id);
    }

    /**
     *
     * @deprecated Use {@link ProductExpansionModel#of()} instead.
     */
    @Deprecated
    public static ProductExpansionModel<Product> expansionPath() {
        return ProductExpansionModel.of();
    }
}
