package io.sphere.sdk.producttypes.expansion;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ProductTypeExpansionModel<T> extends ExpansionModel<T> {
    private ProductTypeExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ProductTypeExpansionModel() {
        super();
    }

    public static ProductTypeExpansionModel<ProductType> of() {
        return new ProductTypeExpansionModel<>();
    }
}
