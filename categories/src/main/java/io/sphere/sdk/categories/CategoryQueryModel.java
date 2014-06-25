package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.*;

public class CategoryQueryModel<T> extends EmbeddedQueryModel<T, CategoryQueryModel<Category>> {
    private static final CategoryQueryModel<CategoryQueryModel<Category>> instance = new CategoryQueryModel<>(Optional.<QueryModel<CategoryQueryModel<Category>>>absent(), Optional.<String>absent());

    public static CategoryQueryModel<CategoryQueryModel<Category>> get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<T> slug() {
        return localizedStringQueryModel("slug");
    }

    public LocalizedStringQuerySortingModel<T> name() {
        return localizedStringQueryModel("name");
    }
    public StringQueryWithSortingModel<T> id() {
        return new StringQueryWithSortingModel<>(Optional.of(this), "id");
    }
}
