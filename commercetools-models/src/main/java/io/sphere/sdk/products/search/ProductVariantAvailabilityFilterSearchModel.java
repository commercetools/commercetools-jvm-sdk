package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFilterSearchModel<T> extends ProductVariantAvailabilityFilterSearchModelCommon<T> {

    @Override
    TermFilterSearchModel<T, Boolean> isOnStock();

    @Override
    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();

    ChannelsProductVariantAvailabilityFilterSearchModel<T> channels();
}
