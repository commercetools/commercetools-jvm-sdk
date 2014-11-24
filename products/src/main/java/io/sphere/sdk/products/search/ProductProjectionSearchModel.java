package io.sphere.sdk.products.search;

import io.sphere.sdk.search.SearchModel;

import java.util.Optional;

public class ProductProjectionSearchModel extends ProductDataSearchModelBase<ProductProjectionSearch> {

    private ProductProjectionSearchModel(final Optional<? extends SearchModel<ProductProjectionSearch>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ProductProjectionSearchModel get() {
        return new ProductProjectionSearchModel(Optional.<SearchModel<ProductProjectionSearch>>empty(), Optional.<String>empty());
    }
}
