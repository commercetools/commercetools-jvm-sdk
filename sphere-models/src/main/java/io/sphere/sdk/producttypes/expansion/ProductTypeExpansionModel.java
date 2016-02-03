package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.producttypes.ProductType;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ProductTypeExpansionModel<T> extends ExpansionModel<T> {
    private ProductTypeExpansionModel() {
    }

    public static ProductTypeExpansionModel<ProductType> of() {
        return new ProductTypeExpansionModel<>();
    }
}
