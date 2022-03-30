package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDsl;


public interface ProductByIdHead extends MetaModelHeadDsl<Product, Product, ProductByIdHead> {
    static ProductByIdHead of(final Identifiable<Product> product) {
        return of(product.getId());
    }

    static ProductByIdHead of(final String id) {
        return new ProductByIdHeadImpl(id);
    }
}

