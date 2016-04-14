package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.MultiValueSortSearchModel;

interface ProductVariantAvailabilitySortSearchModelCommon<T> {
    /**
     * Model to formulate search requests with sorting by restockable in days.
     *
     * <p>Example without channels:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#sortByRestockableInDays()}
      *
     * <p>Example with a channel:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#sortByRestockableInDaysWithSupplyChannel()}
     *
     * @return
     */
    MultiValueSortSearchModel<T> restockableInDays();

    /**
     * Model to formulate search requests with by availableQuantity.
     *
     * <p>Example without channels:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#sortByAvailableQuantity()}
     *
     * <p>Example with a channel:</p>
     * {@include.example io.sphere.sdk.products.search.ProductAvailabilitySearchIntegrationTest#sortByAvailableQuantityWithSupplyChannel()}
     *
     * @return
     */
    MultiValueSortSearchModel<T> availableQuantity();
}
