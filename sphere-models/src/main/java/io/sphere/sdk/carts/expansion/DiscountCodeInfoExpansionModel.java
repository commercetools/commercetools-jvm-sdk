package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathsHolder;

public class DiscountCodeInfoExpansionModel<T> extends ExpansionModel<T> {
    DiscountCodeInfoExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPathsHolder<T> discountCode() {
        return expansionPath("discountCode");
    }
}
