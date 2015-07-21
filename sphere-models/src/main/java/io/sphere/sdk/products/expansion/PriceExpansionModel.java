package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPath<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public ExpansionPath<T> channel() {
        return expansionPath("channel");
    }

    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModel<>(pathExpression(), "discounted");
    }
}

