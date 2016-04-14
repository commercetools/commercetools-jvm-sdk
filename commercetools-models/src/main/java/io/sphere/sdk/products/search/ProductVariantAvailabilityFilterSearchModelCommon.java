package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

interface ProductVariantAvailabilityFilterSearchModelCommon<T> {

    /**
     * Helper to build search requests including products which are available.
     *
     * <p>Example without channels:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForIsOnStock()}
     *
     * <p>Example with a channel:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForIsOnStockWithChannel()}
     *
     * @return model for is on stock
     */
    TermFilterSearchModel<T, Boolean> isOnStock();

    /**
     * Helper to build search requests filtering for ranges of product availability.
     *
     * <p>Example without channels:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForAvailableQuantityRanges()}
     *
     * <p>Example with a channel:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForAvailableQuantityRangesInChannels()}
     *
     * @return model for availableQuantity
     */
    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();


}
