package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.reviews.search.ReviewRatingStatisticsFacetAndFilterSearchModel;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductDataFacetAndFilterSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFacetAndFilterSearchModel allVariants() {
        return new ProductVariantFacetAndFilterSearchModel(this, "variants");
    }

    public TermFacetAndFilterSearchModel<ProductProjection> id() {
        return stringSearchModel("id").facetedAndFiltered();
    }

    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> name() {
        return localizedStringFacetAndFilterSearchModel("name");
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> categories() {
        return referenceFacetAndFilterSearchModel("categories");
    }

    public ReferenceFacetAndFilterSearchModel<ProductProjection> productType() {
        return referenceFacetAndFilterSearchModel("productType");
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> createdAt() {
        return datetimeSearchModel("createdAt").facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").facetedAndFiltered();
    }

    public ReviewRatingStatisticsFacetAndFilterSearchModel<ProductProjection> reviewRatingStatistics() {
        return new ReviewRatingStatisticsFacetAndFilterSearchModel<>(this, "reviewRatingStatistics");
    }
}
