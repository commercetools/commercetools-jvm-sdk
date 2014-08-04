package io.sphere.sdk.products;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class ProductExpansionPath extends ExpansionModel implements ExpansionPath<Product> {
    ProductExpansionPath(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    @Override
    public String toSphereExpand() {
        return internalToSphereExpand();
    }
}
