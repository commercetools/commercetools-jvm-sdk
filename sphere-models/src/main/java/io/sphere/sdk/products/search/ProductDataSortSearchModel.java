package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductDataSortSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantSortSearchModel allVariants() {
        return new ProductVariantSortSearchModel(this, "variants");
    }

    public LocalizedStringSortSearchModel<ProductProjection, SingleValueSortSearchModel<ProductProjection>> name() {
        return new LocalizedStringSortSearchModel<>(this, "name", sortModelBuilder());
    }

    public SingleValueSortSearchModel<ProductProjection> createdAt() {
        return new SortableSearchModel<>(this, "createdAt", sortModelBuilder()).sorted();
    }

    public SingleValueSortSearchModel<ProductProjection> lastModifiedAt() {
        return new SortableSearchModel<>(this, "lastModifiedAt", sortModelBuilder()).sorted();
    }

    private SingleValueSortSearchModelBuilder<ProductProjection> sortModelBuilder() {
        return new SingleValueSortSearchModelBuilder<>();
    }
}
