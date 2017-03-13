package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductVariantAvailability;
import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFilterSearchModel<T> extends ProductVariantAvailabilityFilterSearchModelCommon<T> {

    @Override
    TermFilterSearchModel<T, Boolean> isOnStock();

    /**
     * Returns a filter search model for product variant availabilities by channels with
     * {@link ProductVariantAvailability#isOnStock()} equal to {@code true}.
     *
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForIsOnStockInChannels()}
     *
     * @return a filter search model for specifying the channels by their id
     */
    TermFilterSearchModel<T, String> onStockInChannels();

    @Override
    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();

    ChannelsProductVariantAvailabilityFilterSearchModel<T> channels();
}
