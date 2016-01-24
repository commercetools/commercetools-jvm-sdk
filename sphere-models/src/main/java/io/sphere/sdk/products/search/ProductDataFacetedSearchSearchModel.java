package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public class ProductDataFacetedSearchSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataFacetedSearchSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFacetedSearchSearchModel allVariants() {
        return new ProductVariantFacetedSearchSearchModel(this, "variants");
    }

    public TermFacetedSearchSearchModel<ProductProjection> id() {
        return stringSearchModel("id").facetedAndFiltered();
    }

    public LocalizedStringFacetedSearchSearchModel<ProductProjection> name() {
        return localizedStringFacetedSearchSearchModel("name");
    }

    public ReferenceFacetedSearchSearchModel<ProductProjection> categories() {
        return referenceFacetedSearchSearchModel("categories");
    }

    public ReferenceFacetedSearchSearchModel<ProductProjection> productType() {
        return referenceFacetedSearchSearchModel("productType");
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> createdAt() {
        return datetimeSearchModel("createdAt").facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<ProductProjection> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").facetedAndFiltered();
    }
}
