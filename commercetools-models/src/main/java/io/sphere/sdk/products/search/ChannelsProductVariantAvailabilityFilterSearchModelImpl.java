package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

final class ChannelsProductVariantAvailabilityFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ChannelsProductVariantAvailabilityFilterSearchModel<T> {
    ChannelsProductVariantAvailabilityFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ChannelProductVariantAvailabilityFilterSearchModel<T> channelId(final String channelId) {
        return new ProductVariantAvailabilityFilterSearchModelImpl<>(this, channelId);
    }
}
