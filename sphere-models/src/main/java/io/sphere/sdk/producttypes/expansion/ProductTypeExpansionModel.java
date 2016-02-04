package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface ProductTypeExpansionModel<T> extends ExpansionPathContainer<T> {
    static ProductTypeExpansionModel<ProductType> of() {
        return new ProductTypeExpansionModelImpl<>();
    }

    static <T> ProductTypeExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ProductTypeExpansionModelImpl<>(parentPath, path);
    }
}
