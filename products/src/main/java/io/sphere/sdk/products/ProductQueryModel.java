package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;

public class ProductQueryModel<T> extends EmbeddedQueryModel<T, ProductQueryModel<Product>> {

    private static final ProductQueryModel<ProductQueryModel<Product>> instance = new ProductQueryModel<>(Optional.<QueryModel<ProductQueryModel<Product>>>absent(), Optional.<String>absent());

    public static ProductQueryModel<ProductQueryModel<Product>> get() {
        return instance;
    }

    private ProductQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }
}