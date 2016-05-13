package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * EXPERIMENTAL model to easily build product projection facet requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 *
 * For creation use {@link ProductProjectionSearchModel#facet()}.
 */
public final class ProductProjectionFacetSearchModel extends ProductDataFacetSearchModel {

    ProductProjectionFacetSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantFacetSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public TermFacetSearchModel<ProductProjection, String> id() {
        return super.id();
    }

    @Override
    public LocalizedStringFacetSearchModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public ProductCategoriesReferenceFacetSearchModel<ProductProjection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceFacetSearchModel<ProductProjection> productType() {
        return super.productType();
    }

    @Override
    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return super.createdAt();
    }

    @Override
    public RangeTermFacetSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
