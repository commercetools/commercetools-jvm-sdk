package io.sphere.sdk.products.search;

import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

interface ProductVariantAvailabilityFilterSearchModelCommon<T> {

    TermFilterSearchModel<T, Boolean> isOnStock();

    RangeTermFilterSearchModel<T, BigDecimal> availableQuantity();


}
