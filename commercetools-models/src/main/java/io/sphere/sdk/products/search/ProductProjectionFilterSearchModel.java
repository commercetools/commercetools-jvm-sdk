package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsFilterSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * EXPERIMENTAL model to easily build product projection filter requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public final class ProductProjectionFilterSearchModel extends ProductDataFilterSearchModel {

    ProductProjectionFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantFilterSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public TermFilterSearchModel<ProductProjection, String> id() {
        return super.id();
    }

    @Override
    public LocalizedStringFilterSearchModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public ProductCategoriesReferenceFilterSearchModel<ProductProjection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceFilterSearchModel<ProductProjection> productType() {
        return super.productType();
    }

    @Override
    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return super.createdAt();
    }

    @Override
    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return super.lastModifiedAt();
    }

    @Override
    public ReviewRatingStatisticsFilterSearchModel<ProductProjection> reviewRatingStatistics() {
        return super.reviewRatingStatistics();
    }
}
