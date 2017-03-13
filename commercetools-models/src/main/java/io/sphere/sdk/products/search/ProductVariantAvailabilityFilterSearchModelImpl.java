package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import javax.annotation.Nullable;
import java.math.BigDecimal;

final class ProductVariantAvailabilityFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ProductVariantAvailabilityFilterSearchModel<T>, ChannelProductVariantAvailabilityFilterSearchModel<T> {

    ProductVariantAvailabilityFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public TermFilterSearchModel<T, Boolean> isOnStock() {
        return booleanSearchModel("isOnStock").filtered();
    }

    @Override
    public TermFilterSearchModel<T, String> onStockInChannels() {
        return stringSearchModel("isOnStockInChannels").filtered();
    }

    @Override
    public RangeTermFilterSearchModel<T, BigDecimal> availableQuantity() {
        return numberSearchModel("availableQuantity").filtered();
    }

    @Override
    public ChannelsProductVariantAvailabilityFilterSearchModel<T> channels() {
        return new ChannelsProductVariantAvailabilityFilterSearchModelImpl<>(this, "channels");
    }
}
