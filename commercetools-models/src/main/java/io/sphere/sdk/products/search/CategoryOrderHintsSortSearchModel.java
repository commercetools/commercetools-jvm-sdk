package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public final class CategoryOrderHintsSortSearchModel extends SortableSearchModel<ProductProjection, SingleValueSortSearchModel<ProductProjection>> {
    CategoryOrderHintsSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, SingleValueSortSearchModelFactory.of());
    }

    public SingleValueSortSearchModel<ProductProjection> category(final String categoryId) {
        return searchModel(categoryId).sorted();
    }
}
