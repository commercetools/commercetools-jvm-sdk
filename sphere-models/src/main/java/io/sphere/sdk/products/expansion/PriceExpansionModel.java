package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public final class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ExpansionPathContainer<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public ExpansionPathContainer<T> channel() {
        return expansionPath("channel");
    }

    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModelImpl<>(pathExpression(), "discounted");
    }
}

