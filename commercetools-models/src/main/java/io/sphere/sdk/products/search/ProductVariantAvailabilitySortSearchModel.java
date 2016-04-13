package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MultiValueSortSearchModel;

public interface ProductVariantAvailabilitySortSearchModel<T> {
    MultiValueSortSearchModel<T> restockableInDays();
}
