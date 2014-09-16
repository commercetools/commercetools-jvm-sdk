package io.sphere.sdk.categories.queries;

import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

/**
 * DSL class to create expansion path expressions.
 *
 * @see CategoryQuery#expansionPath()
 */
public class CategoryExpansionModel extends ExpansionModel {
    CategoryExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.ofNullable(path));
    }

    CategoryExpansionModel() {
        super();
    }

    public CategoryExpansionPath ancestors() {
        return new CategoryExpansionPath(path, "ancestors[*]");
    }

    public CategoryExpansionPath parent() {
        return new CategoryExpansionPath(path, "parent");
    }
}
