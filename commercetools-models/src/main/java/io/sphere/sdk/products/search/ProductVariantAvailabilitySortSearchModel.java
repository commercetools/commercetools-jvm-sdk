package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MultiValueSortSearchModel;

public interface ProductVariantAvailabilitySortSearchModel<T> extends ProductVariantAvailabilitySortSearchModelCommon<T> {
    @Override
    MultiValueSortSearchModel<T> restockableInDays();

    ChannelsProductVariantAvailabilitySortSearchModel<T> channels();
}
