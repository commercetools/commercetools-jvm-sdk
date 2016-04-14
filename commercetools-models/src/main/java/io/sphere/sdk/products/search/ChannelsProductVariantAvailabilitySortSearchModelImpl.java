package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

final class ChannelsProductVariantAvailabilitySortSearchModelImpl<T> extends SearchModelImpl<T> implements ChannelsProductVariantAvailabilitySortSearchModel<T> {
    ChannelsProductVariantAvailabilitySortSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ChannelProductVariantAvailabilitySortSearchModel<T> channelId(final String channelId) {
        return new ProductVariantAvailabilitySortSearchModelImpl<>(this, channelId);
    }
}
