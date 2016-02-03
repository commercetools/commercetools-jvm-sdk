package io.sphere.sdk.productdiscounts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productdiscounts.ProductDiscount;

public final class ProductDiscountExpansionModel<T> extends ExpansionModel<T> {
    ProductDiscountExpansionModel() {
    }

    public static ProductDiscountExpansionModel<ProductDiscount> of() {
        return new ProductDiscountExpansionModel<>();
    }

    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
