package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    public ExpansionPath<T> customerGroup() {
        return pathWithRoots("customerGroup");
    }

    public ExpansionPath<T> channel() {
        return pathWithRoots("channel");
    }
}

