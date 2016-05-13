package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;
import io.sphere.sdk.search.model.TypeSerializer;

import javax.annotation.Nullable;
import java.util.List;

final class ProductCategoriesReferenceFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ProductCategoriesReferenceFilterSearchModel<T> {

    protected ProductCategoriesReferenceFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCategoriesIdTermFilterSearchModel<T> id() {
        return new ProductCategoriesIdTermFilterSearchModelImpl<>(new SearchModelImpl<>(this, "id"), TypeSerializer.ofString());
    }

    @Override
    public List<FilterExpression<T>> exists() {
        return existsFilters();
    }

    @Override
    public List<FilterExpression<T>> missing() {
        return missingFilters();
    }
}