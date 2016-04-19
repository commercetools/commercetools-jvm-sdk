package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;

import java.math.BigDecimal;

interface ProductVariantAvailabilityFacetSearchModelCommon<T> {
    RangeTermFacetSearchModel<T, BigDecimal> availableQuantity();
}
