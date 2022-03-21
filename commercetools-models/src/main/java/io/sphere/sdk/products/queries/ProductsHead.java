package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.MetaModelHeadDsl;

public interface ProductsHead extends MetaModelHeadDsl<Product, Product, ProductsHead>{

    static ProductsHead of() {
        return new ProductsHeadImpl();
    }
}
