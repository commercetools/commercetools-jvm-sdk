package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.producttypes.ProductType;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface ProductTypeExpansionModel<T> {
    static ProductTypeExpansionModel<ProductType> of() {
        return new ProductTypeExpansionModelImpl<>();
    }
}
