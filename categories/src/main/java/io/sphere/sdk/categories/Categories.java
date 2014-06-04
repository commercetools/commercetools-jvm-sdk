package io.sphere.sdk.categories;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.sdk.models.Reference;

public final class Categories {

    private Categories() {
    }

    public static CategoryQuery query() {
        return new CategoryQuery();
    }

    public static Reference<Category> reference(final Category category) {
        return new Reference<>("category", category.getId(), Optional.fromNullable(category));
    }

    public static Optional<Reference<Category>> reference(final Optional<Category> category) {
        return category.transform(new Function<Category, Reference<Category>>() {
            @Override
            public Reference<Category> apply(final Category input) {
                return Categories.reference(input);
            }
        });
    }
}
