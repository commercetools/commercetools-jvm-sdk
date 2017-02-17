package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsFilterSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * EXPERIMENTAL model to easily build product projection filter requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 *
 * For creation use {@link ProductProjectionSearchModel#filter()}.
 */
public final class ProductProjectionFilterSearchModel extends SearchModelImpl<ProductProjection> {

    ProductProjectionFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFilterSearchModel allVariants() {
        return new ProductVariantFilterSearchModel(this, "variants");
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> id() {
        return stringSearchModel("id").filtered();
    }

    public TermFilterExistsAndMissingSearchModel<ProductProjection, String> key() {
        return stringSearchModel("key").filtered();
    }

    public LocalizedStringFilterSearchModel<ProductProjection> name() {
        return localizedStringFilterSearchModel("name");
    }

    public LocalizedStringFilterSearchModel<ProductProjection> slug() {
        return localizedStringFilterSearchModel("slug");
    }

    public ProductCategoriesReferenceFilterSearchModel<ProductProjection> categories() {
        return new ProductCategoriesReferenceFilterSearchModelImpl<>(this, "categories");
    }

    public ReferenceFilterSearchModel<ProductProjection> productType() {
        return referenceFilterSearchModel("productType");
    }

    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return datetimeSearchModel("createdAt").filtered();
    }

    public RangeTermFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").filtered();
    }

    public ReviewRatingStatisticsFilterSearchModel<ProductProjection> reviewRatingStatistics() {
        return new ReviewRatingStatisticsFilterSearchModel<>(this, "reviewRatingStatistics");
    }

    public ReferenceFilterSearchModel<ProductProjection> taxCategory() {
        return referenceFilterSearchModel("taxCategory");
    }

    public ReferenceFilterSearchModel<ProductProjection> state() {
        return referenceFilterSearchModel("state");
    }
}
