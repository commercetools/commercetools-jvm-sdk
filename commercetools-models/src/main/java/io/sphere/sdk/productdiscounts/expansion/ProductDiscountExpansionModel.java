package io.sphere.sdk.productdiscounts.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.List;

public interface ProductDiscountExpansionModel<T> extends ExpansionPathContainer<T> {
    ExpansionPathContainer<T> references();

    static ProductDiscountExpansionModel<ProductDiscount> of() {
        return new ProductDiscountExpansionModelImpl<>();
    }

    static <T> ProductDiscountExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ProductDiscountExpansionModelImpl<>(parentPath, path);
    }
}
