package io.sphere.sdk.categories.queries;

import java.util.Optional;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

/**
 * {@doc.gen summary categories}
 */
public class CategoryQueryModel extends DefaultModelQueryModelImpl<Category> {
    private static final CategoryQueryModel instance = new CategoryQueryModel(Optional.<QueryModelImpl<Category>>empty(), Optional.<String>empty());

    static CategoryQueryModel get() {
        return instance;
    }

    private CategoryQueryModel(Optional<? extends QueryModel<Category>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringsQuerySortingModel<Category> slug() {
        return LocalizedStringsQuerySortingModel.of(this, "slug");
    }

    public LocalizedStringsQuerySortingModel<Category> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }

    public StringQuerySortingModel<Category> externalId() {
        return new StringQuerySortingModel<>(Optional.of(this), "externalId");
    }

    public OptionalReferenceQueryModel<Category, Category> parent() {
        return new OptionalReferenceQueryModel<>(Optional.of(this), "parent");
    }
}
