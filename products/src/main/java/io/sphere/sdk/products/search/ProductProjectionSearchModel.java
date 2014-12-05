package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.SearchModel;

import java.util.Optional;

public class ProductProjectionSearchModel extends ProductDataSearchModelBase {

    private ProductProjectionSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ProductProjectionSearchModel get() {
        return new ProductProjectionSearchModel(Optional.empty(), Optional.empty());
    }
}
