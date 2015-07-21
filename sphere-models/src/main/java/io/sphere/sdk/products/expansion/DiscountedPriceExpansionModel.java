package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public final class DiscountedPriceExpansionModel<T> extends ExpansionModel<T> {
    DiscountedPriceExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> discount() {
        return expansionPath("discount");
    }
}

