package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MultiValueSortSearchModel;

interface ProductVariantAvailabilitySortSearchModelCommon<T> {
    MultiValueSortSearchModel<T> restockableInDays();
}
