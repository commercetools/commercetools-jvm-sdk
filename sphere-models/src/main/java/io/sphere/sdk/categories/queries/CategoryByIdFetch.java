package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface CategoryByIdFetch extends MetaModelFetchDsl<Category, CategoryByIdFetch, CategoryExpansionModel<Category>> {
    static CategoryByIdFetch of(final Identifiable<Category> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CategoryByIdFetch of(final String id) {
        return new CategoryByIdFetchImpl(id);
    }

    @Override
    CategoryByIdFetch plusExpansionPath(final Function<CategoryExpansionModel<Category>, ExpansionPath<Category>> m);

    @Override
    CategoryByIdFetch withExpansionPath(final Function<CategoryExpansionModel<Category>, ExpansionPath<Category>> m);

    @Override
    List<ExpansionPath<Category>> expansionPaths();

    @Override
    CategoryByIdFetch plusExpansionPath(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdFetch withExpansionPath(final ExpansionPath<Category> expansionPath);

    @Override
    CategoryByIdFetch withExpansionPath(final List<ExpansionPath<Category>> expansionPaths);
}
