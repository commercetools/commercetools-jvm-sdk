package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsSortSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * EXPERIMENTAL model to easily build product projection sort requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public class ProductProjectionSortSearchModel extends ProductDataSortSearchModel {

    ProductProjectionSortSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantSortSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public LocalizedStringSortSearchModel<ProductProjection, SingleValueSortSearchModel<ProductProjection>> name() {
        return super.name();
    }

    @Override
    public SingleValueSortSearchModel<ProductProjection> createdAt() {
        return super.createdAt();
    }

    @Override
    public SingleValueSortSearchModel<ProductProjection> lastModifiedAt() {
        return super.lastModifiedAt();
    }

    @Override
    public CategoryOrderHintsSortSearchModel categoryOrderHints() {
        return super.categoryOrderHints();
    }

    @Override
    public ReviewRatingStatisticsSortSearchModel<ProductProjection> reviewRatingStatistics() {
        return super.reviewRatingStatistics();
    }
}
