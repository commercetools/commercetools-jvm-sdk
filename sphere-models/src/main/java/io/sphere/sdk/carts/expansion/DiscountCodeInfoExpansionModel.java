package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> discountCode() {
        return expansionPath("discountCode");
    }
}
