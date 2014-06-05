package io.sphere.sdk.categories;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;

public final class Categories {

    public static final String CATEGORY_TYPE_ID = "category";

    private Categories() {
    }

    public static CategoryQuery query() {
        return new CategoryQuery();
    }

    public static Reference<Category> reference(final Category category) {
        return new Reference<>(CATEGORY_TYPE_ID, category.getId(), Optional.fromNullable(category));
    }

    public static Optional<Reference<Category>> reference(final Optional<Category> category) {
        return category.transform(new Function<Category, Reference<Category>>() {
            @Override
            public Reference<Category> apply(final Category input) {
                return Categories.reference(input);
            }
        });
    }

    public static Reference<Category> reference(final String id) {
        return Reference.of(CATEGORY_TYPE_ID, id);
    }

    public static String toString(final Category category) {
        return Objects.toStringHelper(category.getClass()).
                add("id", category.getId()).
                add("version", category.getVersion()).
                add("createdAt", category.getCreatedAt()).
                add("lastModifiedAt", category.getLastModifiedAt()).
                add("name", category.getName()).
                add("slug", category.getSlug()).
                add("description", category.getDescription()).
                add("ancestors", Joiner.on(", ").join(category.getAncestors())).
                add("parent", category.getParent()).
                add("orderHint", category.getOrderHint()).
                add("children", category.getChildren()).
                add("pathInTree", Joiner.on(", ").join(Iterables.transform(category.getPathInTree(), new Function<Category, Object>() {
                    @Override
                    public String apply(final Category input) {
                        return input.getId();
                    }
                }))).toString();
    }
}
