package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.ReferenceFilterSearchModel;

public interface ProductCategoriesReferenceFilterSearchModel<T> extends ReferenceFilterSearchModel<T> {
    ProductCategoriesIdTermFilterSearchModel<T> id();
}
