package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.TermFilterSearchModel;

public interface ProductVariantAvailabilityFilterSearchModel {

    TermFilterSearchModel<ProductProjection, Boolean> isOnStock();
}
