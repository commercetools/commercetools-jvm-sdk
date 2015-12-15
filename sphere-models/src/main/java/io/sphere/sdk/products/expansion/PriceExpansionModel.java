package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathsHolder;

import java.util.List;

public class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPathsHolder<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public ExpansionPathsHolder<T> channel() {
        return expansionPath("channel");
    }

    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModel<>(pathExpression(), "discounted");
    }
}

