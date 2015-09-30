package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;
import io.sphere.sdk.types.queries.CustomQueryModelImpl;

/**
 * {@doc.gen summary categories}
 */
public final class CategoryQueryModel extends ResourceQueryModelImpl<Category> {

    public static CategoryQueryModel of() {
        return new CategoryQueryModel(null, null);
    }

    private CategoryQueryModel(QueryModel<Category> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<Category> slug() {
        return localizedStringQuerySortingModel("slug");
    }

    public LocalizedStringQuerySortingModel<Category> name() {
        return localizedStringQuerySortingModel("name");
    }

    public StringQuerySortingModel<Category> externalId() {
        return stringModel("externalId");
    }

    public ReferenceOptionalQueryModel<Category, Category> parent() {
        return referenceOptionalModel("parent");
    }

    public CustomQueryModel<Category> custom() {
        return new CustomQueryModelImpl<>(this, "custom");
    }
}
