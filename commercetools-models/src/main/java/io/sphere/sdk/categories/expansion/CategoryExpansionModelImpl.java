package io.sphere.sdk.categories.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class CategoryExpansionModelImpl<T> extends ExpansionModelImpl<T> implements CategoryExpansionModel<T> {
    CategoryExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CategoryExpansionModelImpl() {
        super();
    }

    @Override
    public CategoryExpansionModel<T> ancestors(final int index) {
        return new CategoryExpansionModelImpl<>(pathExpression(), collection("ancestors", index));
    }

    @Override
    public CategoryExpansionModel<T> ancestors() {
        return new CategoryExpansionModelImpl<>(pathExpression(), "ancestors[*]");
    }

    @Override
    public CategoryExpansionModel<T> parent() {
        return new CategoryExpansionModelImpl<>(pathExpression(), "parent");
    }
}
