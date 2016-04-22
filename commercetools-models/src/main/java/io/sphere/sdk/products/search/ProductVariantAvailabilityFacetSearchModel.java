package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFacetSearchModel<T> extends ProductVariantAvailabilityFacetSearchModelCommon<T> {
    @Override
    RangeTermFacetSearchModel<T, BigDecimal> availableQuantity();

    ChannelsProductVariantAvailabilityFacetSearchModel<T> channels();
}
