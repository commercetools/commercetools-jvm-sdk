package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

/**
 * EXPERIMENTAL model to easily build product projection search requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public class ProductProjectionSearchModel extends ProductDataSearchModelBase {

    private ProductProjectionSearchModel(@Nullable final SearchModel<ProductProjection> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static ProductProjectionSearchModel of() {
        return new ProductProjectionSearchModel(null, null);
    }

    @Override
    public ProductVariantSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public LocalizedStringSearchModel<ProductProjection, SimpleSearchSortDirection> name() {
        return super.name();
    }

    @Override
    public ReferenceSearchModel<ProductProjection, SimpleSearchSortDirection> categories() {
        return super.categories();
    }

    @Override
    public ReferenceSearchModel<ProductProjection, SimpleSearchSortDirection> productType() {
        return super.productType();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> createdAt() {
        return super.createdAt();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
