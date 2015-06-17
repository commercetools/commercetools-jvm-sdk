package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public ExpansionPath<T> customerGroup() {
        return expansionPath("customerGroup");
    }

    public ExpansionPath<T> channel() {
        return expansionPath("channel");
    }
}

