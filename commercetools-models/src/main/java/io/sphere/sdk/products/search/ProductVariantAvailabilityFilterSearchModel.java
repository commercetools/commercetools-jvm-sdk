package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.math.BigDecimal;

public interface ProductVariantAvailabilityFilterSearchModel {

    TermFilterSearchModel<ProductProjection, Boolean> isOnStock();

    RangeTermFilterSearchModel<ProductProjection, BigDecimal> availableQuantity();
}
