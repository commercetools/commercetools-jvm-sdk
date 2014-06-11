package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.queries.EmbeddedQueryModel;
import io.sphere.sdk.queries.LocalizedStringQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public class CategoryQueryModel<T> extends EmbeddedQueryModel<T, CategoryQueryModel<Category>> {
    private static final CategoryQueryModel<CategoryQueryModel<Category>> instance = new CategoryQueryModel<>(Optional.<QueryModel<CategoryQueryModel<Category>>>absent(), Optional.<String>absent());

    public static CategoryQueryModel<CategoryQueryModel<Category>> get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQueryModel<T> slug() {
        return localizedStringQueryModel("slug");
    }

    public LocalizedStringQueryModel<T> name() {
        return localizedStringQueryModel("name");
    }
    public StringQueryModel<T> id() {
        return new StringQueryModel<>(Optional.of(this), "id");
    }
}
