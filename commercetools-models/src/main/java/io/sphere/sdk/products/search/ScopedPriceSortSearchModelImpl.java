package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

final class ScopedPriceSortSearchModelImpl<T> extends SortableSearchModel<T, MultiValueSortSearchModel<T>> implements ScopedPriceSortSearchModel<T> {
    ScopedPriceSortSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, MultiValueSortSearchModelFactory.of());
    }

    @Override
    public MoneySortSearchModel<T, MultiValueSortSearchModel<T>> currentValue() {
        return moneySortSearchModel("currentValue");
    }

    @Override
    public MoneySortSearchModel<T, MultiValueSortSearchModel<T>> value() {
        return moneySortSearchModel("value");
    }

    @Override
    public DiscountedPriceSortSearchModel<T> discounted() {
        return new DiscountedPriceSortSearchModelImpl<>(this, "discounted");
    }
}
