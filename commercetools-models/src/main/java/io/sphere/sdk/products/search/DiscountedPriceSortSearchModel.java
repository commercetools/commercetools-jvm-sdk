package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MoneySortSearchModel;
import io.sphere.sdk.search.model.MultiValueSortSearchModel;

public interface DiscountedPriceSortSearchModel<T> {

    MoneySortSearchModel<T, MultiValueSortSearchModel<T>> value();
}
