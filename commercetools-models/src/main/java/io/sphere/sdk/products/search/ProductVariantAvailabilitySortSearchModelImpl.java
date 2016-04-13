package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MultiValueSortSearchModel;
import io.sphere.sdk.search.model.MultiValueSortSearchModelFactory;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SortableSearchModel;

import javax.annotation.Nullable;

final class ProductVariantAvailabilitySortSearchModelImpl<T> extends SortableSearchModel<T, MultiValueSortSearchModel<T>> implements ProductVariantAvailabilitySortSearchModel<T>, ChannelProductVariantAvailabilitySortSearchModel<T> {
    ProductVariantAvailabilitySortSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, MultiValueSortSearchModelFactory.of());
    }

    @Override
    public MultiValueSortSearchModel<T> restockableInDays() {
        return searchModel("restockableInDays").sorted();
    }

    @Override
    public ChannelsProductVariantAvailabilitySortSearchModel<T> channels() {
        return new ChannelsProductVariantAvailabilitySortSearchModelImpl<>(this, "channels");
    }

    @Override
    public MultiValueSortSearchModel<T> availableQuantity() {
        return searchModel("availableQuantity").sorted();
    }
}
