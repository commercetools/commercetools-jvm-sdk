package io.sphere.sdk.categories.queries;

import java.util.Optional;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

public class CategoryQueryModel extends EmbeddedQueryModel<Category> {
    private static final CategoryQueryModel instance = new CategoryQueryModel(Optional.<EmbeddedQueryModel<Category>>empty(), Optional.<String>empty());

    public static CategoryQueryModel get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<Category>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<Category> slug() {
        return localizedStringQueryModel("slug");
    }

    public LocalizedStringQuerySortingModel<Category> name() {
        return localizedStringQueryModel("name");
    }

    public StringQuerySortingModel<Category> id() {
        return new StringQuerySortingModel<>(Optional.of(this), "id");
    }
}
