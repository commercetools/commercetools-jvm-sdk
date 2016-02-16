package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Retrieves a category by a known ID.
 *
 * {@include.example io.sphere.sdk.categories.queries.CategoryByIdGetIntegrationTest#execution()}
 */
public interface CategoryByIdGet extends MetaModelGetDsl<Category, Category, CategoryByIdGet, CategoryExpansionModel<Category>> {
    static CategoryByIdGet of(final Identifiable<Category> category) {
        return of(category.getId());
    }

    static CategoryByIdGet of(final String id) {
        return new CategoryByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Category>> expansionPaths();

    @Override
    CategoryByIdGet plusExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdGet withExpansionPaths(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdGet withExpansionPaths(final List<ExpansionPath<Category>> expansionPaths);
}
