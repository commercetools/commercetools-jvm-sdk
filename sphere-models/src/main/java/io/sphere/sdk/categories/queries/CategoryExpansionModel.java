package io.sphere.sdk.categories.queries;

import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;
/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class CategoryExpansionModel<T> extends ExpansionModel<T> {
    public CategoryExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    CategoryExpansionModel() {
        super();
    }

    public CategoryExpansionModel<T> ancestors() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "ancestors[*]");
    }

    public CategoryExpansionModel<T> parent() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "parent");
    }
}
