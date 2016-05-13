package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.ReferenceFilterSearchModel;

import java.util.List;

public interface ProductCategoriesReferenceFilterSearchModel<T> extends ReferenceFilterSearchModel<T> {
    ProductCategoriesIdTermFilterSearchModel<T> id();

    @Override
    List<FilterExpression<T>> exists();

    @Override
    List<FilterExpression<T>> missing();
}
