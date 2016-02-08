package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Base;

/**
 * EXPERIMENTAL model to easily build product projection search requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public final class ProductProjectionSearchModel extends Base {

    public ProductProjectionFilterSearchModel filter() {
        return new ProductProjectionFilterSearchModel(null, null);
    }

    public ProductProjectionFacetSearchModel facet() {
        return new ProductProjectionFacetSearchModel(null, null);
    }

    public ProductProjectionSortSearchModel sort() {
        return new ProductProjectionSortSearchModel(null, null);
    }

    public ProductProjectionFacetedSearchSearchModel facetedSearch() {
        return new ProductProjectionFacetedSearchSearchModel(null, null);
    }

    public static ProductProjectionSearchModel of() {
        return new ProductProjectionSearchModel();
    }
}
