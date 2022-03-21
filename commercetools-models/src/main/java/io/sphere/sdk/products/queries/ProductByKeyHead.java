package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDsl;

public interface ProductByKeyHead extends MetaModelHeadDsl<Product, Product, ProductByKeyHead> {

    static ProductByKeyHead of(final String key) {
        return new ProductByKeyHeadImpl(key);
    }
}
