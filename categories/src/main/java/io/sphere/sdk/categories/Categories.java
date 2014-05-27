package io.sphere.sdk.categories;

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
}
