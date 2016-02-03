package io.sphere.sdk.categories.expansion;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class CategoryExpansionModel<T> extends ExpandedModel<T> {
    public CategoryExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CategoryExpansionModel() {
        super();
    }

    public CategoryExpansionModel<T> ancestors(final int index) {
        return new CategoryExpansionModel<>(pathExpression(), collection("ancestors", index));
    }

    public CategoryExpansionModel<T> ancestors() {
        return new CategoryExpansionModel<>(pathExpression(), "ancestors[*]");
    }

    public CategoryExpansionModel<T> parent() {
        return new CategoryExpansionModel<>(pathExpression(), "parent");
    }

    public static CategoryExpansionModel<Category> of() {
        return new CategoryExpansionModel<>();
    }
}
