package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface CategoryByIdFetch extends MetaModelFetchDsl<Category, CategoryByIdFetch, CategoryExpansionModel<Category>> {
    static CategoryByIdFetch of(final Identifiable<Category> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CategoryByIdFetch of(final String id) {
        return new CategoryByIdFetchImpl(id);
    }
}
