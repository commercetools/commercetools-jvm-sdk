package io.sphere.sdk.categories;

import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class CategoryExpansionPath extends CategoryExpansionModel implements ExpansionPath<Category> {

    CategoryExpansionPath(final Optional<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public String toSphereExpand() {
        return internalToSphereExpand();
    }
}
