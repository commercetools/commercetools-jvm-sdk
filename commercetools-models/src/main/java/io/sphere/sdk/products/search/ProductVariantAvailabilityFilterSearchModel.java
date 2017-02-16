package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductVariantAvailability;
import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFilterSearchModel<T> extends ProductVariantAvailabilityFilterSearchModelCommon<T> {

    @Override
    TermFilterSearchModel<T, Boolean> isOnStock();

    /**
     * Returns a search model for product variant availabilities with {@link ProductVariantAvailability#isOnStock()}
     * equal to <code></code> in at least one of the channels specified with
     * {@link IsOnStockInChannelsSearchModel#channels(Iterable)}.
     *
     * @return the search model for <code>isOnStockInChannels</code>
     */
    IsOnStockInChannelsSearchModel<T> isOnStockInChannels();

    @Override
    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();

    ChannelsProductVariantAvailabilityFilterSearchModel<T> channels();
}
