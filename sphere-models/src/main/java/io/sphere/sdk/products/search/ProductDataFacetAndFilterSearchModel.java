package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class ProductDataFacetAndFilterSearchModel extends SearchModelImpl<ProductProjection> {

    public ProductDataFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantFacetAndFilterSearchModel allVariants() {
        return new ProductVariantFacetAndFilterSearchModel(this, "variants");
    }

    public TermFacetAndFilterSearchModel<ProductProjection, String> id() {
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

    public RangeTermFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return datetimeSearchModel("createdAt").facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return datetimeSearchModel("lastModifiedAt").facetedAndFiltered();
    }
}
