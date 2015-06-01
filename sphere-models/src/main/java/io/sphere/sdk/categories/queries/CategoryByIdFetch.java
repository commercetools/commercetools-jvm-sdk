package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class CategoryByIdFetch extends ByIdFetchImpl<Category> {
    private CategoryByIdFetch(final String id) {
        super(id, CategoriesEndpoint.ENDPOINT);
    }

    public static CategoryByIdFetch of(final Identifiable<Category> category) {
        return of(category.getId());
    }

    public static CategoryByIdFetch of(final String id) {
        return new CategoryByIdFetch(id);
    }

    public static CategoryExpansionModel<Category> expansionPath() {
        return new CategoryExpansionModel<>();
    }
}
