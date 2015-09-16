package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface CategoryByIdGet extends MetaModelGetDsl<Category, Category, CategoryByIdGet, CategoryExpansionModel<Category>> {
    static CategoryByIdGet of(final Identifiable<Category> category) {
        return of(category.getId());
    }

    static CategoryByIdGet of(final String id) {
        return new CategoryByIdGetImpl(id);
    }

    @Override
    CategoryByIdGet plusExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPath<Category>> m);

    @Override
    CategoryByIdGet withExpansionPaths(final Function<CategoryExpansionModel<Category>, ExpansionPath<Category>> m);

    @Override
    List<ExpansionPath<Category>> expansionPaths();

    @Override
    CategoryByIdGet plusExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdGet withExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdGet withExpansionPaths(final List<ExpansionPath<Category>> expansionPaths);
}
