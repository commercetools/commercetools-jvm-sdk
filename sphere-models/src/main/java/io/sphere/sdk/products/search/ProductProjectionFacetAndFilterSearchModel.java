package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * EXPERIMENTAL model to easily build product projection faceted search requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public class ProductProjectionFacetAndFilterSearchModel extends ProductDataFacetAndFilterSearchModel {

    ProductProjectionFacetAndFilterSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductVariantFacetAndFilterSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public TermFacetAndFilterSearchModel<ProductProjection, String> id() {
        return super.id();
    }

    @Override
    public LocalizedStringFacetAndFilterSearchModel<ProductProjection> name() {
        return super.name();
    }

    @Override
    public ReferenceFacetAndFilterSearchModel<ProductProjection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceFacetAndFilterSearchModel<ProductProjection> productType() {
        return super.productType();
    }

    @Override
    public RangeFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> createdAt() {
        return super.createdAt();
    }

    @Override
    public RangeFacetAndFilterSearchModel<ProductProjection, ZonedDateTime> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
