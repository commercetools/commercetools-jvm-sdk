package io.sphere.sdk.categories;

import java.util.Optional;
import io.sphere.sdk.queries.*;

public class CategoryQueryModel<T> extends EmbeddedQueryModel<T, CategoryQueryModel<Category>> {
    private static final CategoryQueryModel<CategoryQueryModel<Category>> instance = new CategoryQueryModel<>(Optional.<QueryModel<CategoryQueryModel<Category>>>empty(), Optional.<String>empty());

    public static CategoryQueryModel<CategoryQueryModel<Category>> get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<T> slug() {
        return localizedSlugModel();
    }

    public LocalizedStringQuerySortingModel<T> name() {
        return localizedNameModel();
    }

    public StringQuerySortingModel<T> id() {
        return idModel();
    }
}
