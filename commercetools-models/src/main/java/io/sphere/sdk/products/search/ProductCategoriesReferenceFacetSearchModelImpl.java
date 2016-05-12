package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.ReferenceFacetSearchModelImpl;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;
import io.sphere.sdk.search.model.TypeSerializer;

import javax.annotation.Nullable;

final class ProductCategoriesReferenceFacetSearchModelImpl<T> extends ReferenceFacetSearchModelImpl<T> implements ProductCategoriesReferenceFacetSearchModel<T> {
    public ProductCategoriesReferenceFacetSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ProductCategoriesIdTermFacetSearchModel<T> id() {
        return new ProductCategoriesIdTermFacetSearchModelImpl<>(new SearchModelImpl<>(this, "id"), TypeSerializer.ofString());
    }

}
