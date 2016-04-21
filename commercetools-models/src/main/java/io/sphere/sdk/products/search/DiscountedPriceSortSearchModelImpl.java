package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

final class DiscountedPriceSortSearchModelImpl<T> extends SortableSearchModel<T, MultiValueSortSearchModel<T>> implements DiscountedPriceSortSearchModel<T> {
    DiscountedPriceSortSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, MultiValueSortSearchModelFactory.of());
    }

    @Override
    public MoneySortSearchModel<T, MultiValueSortSearchModel<T>> value() {
        return moneySortSearchModel("value");
    }
}
