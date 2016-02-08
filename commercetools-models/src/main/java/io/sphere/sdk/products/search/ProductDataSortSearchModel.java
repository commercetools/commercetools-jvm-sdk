package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsSortSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public abstract class ProductDataSortSearchModel extends SortableSearchModel<ProductProjection, SingleValueSortSearchModel<ProductProjection>> {

    ProductDataSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, SingleValueSortSearchModelFactory.of());
    }

    public ProductVariantSortSearchModel allVariants() {
        return new ProductVariantSortSearchModel(this, "variants");
    }

    public LocalizedStringSortSearchModel<ProductProjection, SingleValueSortSearchModel<ProductProjection>> name() {
        return localizedStringSortSearchModel("name");
    }

    public SingleValueSortSearchModel<ProductProjection> createdAt() {
        return searchModel("createdAt").sorted();
    }

    public SingleValueSortSearchModel<ProductProjection> lastModifiedAt() {
        return searchModel("lastModifiedAt").sorted();
    }

    public CategoryOrderHintsSortSearchModel categoryOrderHints() {
        return new CategoryOrderHintsSortSearchModel(this, "categoryOrderHints");
    }


    public ReviewRatingStatisticsSortSearchModel<ProductProjection> reviewRatingStatistics() {
        return new ReviewRatingStatisticsSortSearchModel<>(this, "reviewRatingStatistics");
    }
}
