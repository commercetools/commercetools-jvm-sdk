package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;

import java.math.BigDecimal;

public interface ChannelProductVariantAvailabilityFacetSearchModel<T> extends ProductVariantAvailabilityFacetSearchModelCommon<T> {
    @Override
    RangeTermFacetSearchModel<T, BigDecimal> availableQuantity();
}
