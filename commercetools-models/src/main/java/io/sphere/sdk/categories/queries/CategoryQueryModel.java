package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

/**
 * {@doc.gen summary categories}
 */
public interface CategoryQueryModel extends QueryModel<Category>, ResourceQueryModel<Category>, WithCustomQueryModel<Category> {
    LocalizedStringQuerySortingModel<Category> slug();

    LocalizedStringQuerySortingModel<Category> name();

    StringQuerySortingModel<Category> externalId();

    ReferenceOptionalQueryModel<Category, Category> parent();

    @Override
    CustomQueryModel<Category> custom();

    @Override
    QueryPredicate<Category> is(Identifiable<Category> identifiable);

    static CategoryQueryModel of() {
        return new CategoryQueryModelImpl(null, null);
    }
}
