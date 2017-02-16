package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

/**
 * Search model for specifying the channels for the <code>isOnStockInChannels</code> product variant
 * availability search.
 *
 * Example usage:
 * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#searchForIsOnStockInChannels()}
 */
public interface IsOnStockInChannelsSearchModel<T> {
    /**
     * Specifies the channels for the search.
     *
     * @param channels list of channel ids
     *
     * @return search filter expressions
     */
    List<FilterExpression<T>> channels(Iterable<String> channels);
}
