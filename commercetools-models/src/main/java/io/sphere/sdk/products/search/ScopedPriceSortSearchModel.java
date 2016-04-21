package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MoneySortSearchModel;
import io.sphere.sdk.search.model.MultiValueSortSearchModel;

public interface ScopedPriceSortSearchModel<T> {

    MoneySortSearchModel<T, MultiValueSortSearchModel<T>> currentValue();

    MoneySortSearchModel<T, MultiValueSortSearchModel<T>> value();

    DiscountedPriceSortSearchModel<T> discounted();
}
