package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;
import java.math.BigDecimal;

final class ProductVariantAvailabilityFacetSearchModelImpl<T> extends SearchModelImpl<T> implements ProductVariantAvailabilityFacetSearchModel<T>, ChannelProductVariantAvailabilityFacetSearchModel<T> {
    ProductVariantAvailabilityFacetSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFacetSearchModel<T, BigDecimal> availableQuantity() {
        return numberSearchModel("availableQuantity").faceted();
    }

    @Override
    public ChannelsProductVariantAvailabilityFacetSearchModel<T> channels() {
        return new ChannelsProductVariantAvailabilityFacetSearchModelImpl<>(this, "channels");
    }
}
