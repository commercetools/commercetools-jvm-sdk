package io.sphere.sdk.categories.queries;

import java.util.Optional;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.*;

/**
 * {@doc.gen summary categories}
 */
public final class CategoryQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static CategoryQueryModel<Category> of() {
        return new CategoryQueryModel<>(Optional.empty(), Optional.<String>empty());
    }

    private CategoryQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringsQuerySortingModel<T> slug() {
        return LocalizedStringsQuerySortingModel.of(this, "slug");
    }

    public LocalizedStringsQuerySortingModel<T> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }

    public StringQuerySortingModel<T> externalId() {
        return new StringQuerySortingModel<>(Optional.of(this), "externalId");
    }

    public OptionalReferenceQueryModel<T, T> parent() {
        return new OptionalReferenceQueryModel<>(Optional.of(this), "parent");
    }
}
