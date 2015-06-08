package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface ProductByIdFetch extends MetaModelFetchDsl<Product, ProductByIdFetch, ProductExpansionModel<Product>> {
    static ProductByIdFetch of(final Identifiable<Product> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ProductByIdFetch of(final String id) {
        return new ProductByIdFetchImpl(id);
    }
}

