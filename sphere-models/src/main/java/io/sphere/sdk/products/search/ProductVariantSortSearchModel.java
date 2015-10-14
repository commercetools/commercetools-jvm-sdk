package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductVariantSortSearchModel extends SearchModelImpl<ProductProjection> {

    ProductVariantSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductAttributeSortSearchModel attribute() {
        return new ProductAttributeSortSearchModel(this, "attributes");
    }

    public MultiValueSortSearchModel<ProductProjection> price() {
        return new SortableSearchModel<>(null, "price", sortModelBuilder()).sorted();
    }

    private MultiValueSortSearchModelBuilder<ProductProjection> sortModelBuilder() {
        return new MultiValueSortSearchModelBuilder<>();
    }
}
