package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.search.ReferenceListSearchModel;
import io.sphere.sdk.search.SearchModel;
import io.sphere.sdk.search.SearchModelImpl;

import java.util.Optional;

class ProductDataSearchModelBase<T> extends SearchModelImpl<T> {

    public ProductDataSearchModelBase(Optional<? extends SearchModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public ProductVariantSearchModel<T> variants() {
        return new ProductVariantSearchModel<>(Optional.of(this), "variants");
    }

    public ReferenceListSearchModel<T, Category> categories() {
        return new ReferenceListSearchModel<>(Optional.of(this), "categories");
    }
}
