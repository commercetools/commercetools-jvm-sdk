package io.sphere.sdk.categories.expansion;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface CategoryExpansionModel<T> extends ExpansionPathContainer<T> {
    CategoryExpansionModel<T> ancestors(int index);

    CategoryExpansionModel<T> ancestors();

    CategoryExpansionModel<T> parent();

    static CategoryExpansionModel<Category> of() {
        return new CategoryExpansionModelImpl<>();
    }

    static <T> CategoryExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new CategoryExpansionModelImpl<>(parentPath, path);
    }
}
