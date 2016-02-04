package io.sphere.sdk.productdiscounts.expansion;

import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productdiscounts.ProductDiscount;

import java.util.List;

public final class ProductDiscountExpansionModel<T> extends ExpandedModel<T> {
    ProductDiscountExpansionModel() {
    }

    ProductDiscountExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public static ProductDiscountExpansionModel<ProductDiscount> of() {
        return new ProductDiscountExpansionModel<>();
    }

    public static <T> ProductDiscountExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ProductDiscountExpansionModel<>(parentPath, path);
    }

    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
