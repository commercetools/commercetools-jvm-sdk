package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import java.util.List;

public class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ReferenceExpansionSupport<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public ReferenceExpansionSupport<T> channel() {
        return expansionPath("channel");
    }

    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModel<>(pathExpression(), "discounted");
    }
}

