package io.sphere.sdk.productdiscounts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.productdiscounts.ProductDiscount;

public class ProductDiscountExpansionModel<T> extends ExpansionModel<T> {
    ProductDiscountExpansionModel() {
    }

    public static ProductDiscountExpansionModel<ProductDiscount> of() {
        return new ProductDiscountExpansionModel<>();
    }

    public ExpansionPath<T> references() {
        return expansionPath("references[*]");
    }
}
