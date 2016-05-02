package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

/**
 * EXPERIMENTAL model to easily build product projection faceted search requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 *
 * For creation use {@link ProductProjectionSearchModel#facetedSearch()}.
 */
public final class ProductProjectionFacetedSearchSearchModel extends ProductDataFacetedSearchSearchModel {

    ProductProjectionFacetedSearchSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantFacetedSearchSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public TermFacetedSearchSearchModel<ProductProjection> id() {
        return super.id();
    }

    @Override
    public LocalizedStringFacetedSearchSearchModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public ReferenceFacetedSearchSearchModel<ProductProjection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceFacetedSearchSearchModel<ProductProjection> productType() {
        return super.productType();
    }

    @Override
    public RangeTermFacetedSearchSearchModel<ProductProjection> createdAt() {
        return super.createdAt();
    }

    @Override
    public RangeTermFacetedSearchSearchModel<ProductProjection> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
