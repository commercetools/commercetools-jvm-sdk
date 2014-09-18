package io.sphere.sdk.categories.queries;

import java.util.Optional;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

/**
 * {@doc.gen summary categories}
 */
public class CategoryQueryModel extends QueryModelImpl<Category> {
    private static final CategoryQueryModel instance = new CategoryQueryModel(Optional.<QueryModelImpl<Category>>empty(), Optional.<String>empty());

    static CategoryQueryModel get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<Category>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<Category> slug() {
        return LocalizedStringQuerySortingModel.<Category>of(this, "slug");
    }

    public LocalizedStringQuerySortingModel<Category> name() {
        return LocalizedStringQuerySortingModel.<Category>of(this, "name");
    }

    public StringQuerySortingModel<Category> id() {
        return new StringQuerySortingModel<>(Optional.of(this), "id");
    }

    public StringQuerySortingModel<Category> externalId() {
        return new StringQuerySortingModel<>(Optional.of(this), "externalId");
    }
}
