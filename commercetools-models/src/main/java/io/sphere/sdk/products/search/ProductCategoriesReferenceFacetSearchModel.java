package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.ReferenceFacetSearchModel;

public interface ProductCategoriesReferenceFacetSearchModel<T> extends ReferenceFacetSearchModel<T> {
    @Override
    ProductCategoriesIdTermFacetSearchModel<T> id();
}
