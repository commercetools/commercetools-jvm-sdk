package io.sphere.sdk.products.expansion;

import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

public interface DiscountedPriceExpansionModel<T> {
    ProductDiscountExpansionModel<T> discount();
}
