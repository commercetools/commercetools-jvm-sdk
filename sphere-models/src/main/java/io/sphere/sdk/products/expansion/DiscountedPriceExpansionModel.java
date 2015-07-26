package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import javax.annotation.Nullable;

public final class DiscountedPriceExpansionModel<T> extends ExpansionModel<T> {
    DiscountedPriceExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> discount() {
        return expansionPath("discount");
    }
}

