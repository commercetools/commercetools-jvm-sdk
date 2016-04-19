package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;

final class ChannelsProductVariantAvailabilityFacetSearchModelImpl<T> extends SearchModelImpl<T> implements ChannelsProductVariantAvailabilityFacetSearchModel<T> {
    ChannelsProductVariantAvailabilityFacetSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ChannelProductVariantAvailabilityFacetSearchModel<T> channelId(final String channelId) {
        return new ProductVariantAvailabilityFacetSearchModelImpl<>(this, channelId);
    }
}
