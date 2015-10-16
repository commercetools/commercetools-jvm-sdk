package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductVariantSortSearchModel extends SortableSearchModel<ProductProjection, MultiValueSortSearchModel<ProductProjection>> {

    ProductVariantSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, MultiValueSortSearchModelFactory.of());
    }

    public ProductAttributeSortSearchModel attribute() {
        return new ProductAttributeSortSearchModel(this, "attributes");
    }

    public MultiValueSortSearchModel<ProductProjection> price() {
        return searchModel(null, "price").sorted();
    }
}
