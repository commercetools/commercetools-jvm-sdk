package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class ProductProjectionExpansionPath extends ExpansionModel implements ExpansionPath<ProductProjection> {
    ProductProjectionExpansionPath(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    @Override
    public String toSphereExpand() {
        return internalToSphereExpand();
    }
}
