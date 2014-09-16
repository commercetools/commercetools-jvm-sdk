package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

/**
 * DSL class to create expansion path expressions.
 *
 * @see CategoryQuery#expansionPath()
 */
public class CategoryExpansionPath extends CategoryExpansionModel implements ExpansionPath<Category> {

    CategoryExpansionPath(final Optional<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public String toSphereExpand() {
        return internalToSphereExpand();
    }
}
