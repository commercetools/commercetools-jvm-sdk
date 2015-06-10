package io.sphere.sdk.categories.expansion;

import io.sphere.sdk.categories.Category;
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

    public CategoryExpansionModel<T> ancestors(final int index) {
        return new CategoryExpansionModel<>(pathExpressionOption(), collection("ancestors", index));
    }

    public CategoryExpansionModel<T> ancestors() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "ancestors[*]");
    }

    public CategoryExpansionModel<T> parent() {
        return new CategoryExpansionModel<>(pathExpressionOption(), "parent");
    }

    public static CategoryExpansionModel<Category> of() {
        return new CategoryExpansionModel<>();
    }
}
