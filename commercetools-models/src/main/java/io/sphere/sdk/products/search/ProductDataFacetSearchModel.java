package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsFacetSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public abstract class ProductDataFacetSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFacetSearchModel allVariants() {
        return new ProductVariantFacetSearchModel(this, "variants");
    }

    public TermFacetSearchModel<ProductProjection, String> id() {
        return stringSearchModel("id").faceted();
    }

    public LocalizedStringFacetSearchModel<ProductProjection> name() {
        return localizedStringFacetSearchModel("name");
    }

    public ProductCategoriesReferenceFacetSearchModel<ProductProjection> categories() {
        return new ProductCategoriesReferenceFacetSearchModelImpl<>(this, "categories");
    }

    public ReferenceFacetSearchModel<ProductProjection> productType() {
        return referenceFacetSearchModel("productType");
    }

    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return datetimeSearchModel("createdAt").faceted();
    }

    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").faceted();
    }

    public ReviewRatingStatisticsFacetSearchModel<ProductProjection> reviewRatingStatistics() {
        return new ReviewRatingStatisticsFacetSearchModel<>(this, "reviewRatingStatistics");
    }
}
