package io.sphere.sdk.producttypes;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.QueryModel;

public class ProductTypeQueryModel<T> extends EmbeddedQueryModel<T, ProductTypeQueryModel<ProductType>> {
    private static final ProductTypeQueryModel<ProductTypeQueryModel<ProductType>> instance = new ProductTypeQueryModel<>(Optional.<QueryModel<ProductTypeQueryModel<ProductType>>>absent(), Optional.<String>absent());

    public static ProductTypeQueryModel<ProductTypeQueryModel<ProductType>> get() {
        return instance;
    }

    private ProductTypeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }
}
