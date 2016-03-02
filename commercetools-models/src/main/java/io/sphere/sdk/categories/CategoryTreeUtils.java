package io.sphere.sdk.categories;

import io.sphere.sdk.models.Identifiable;

import static java.lang.String.format;

final class CategoryTreeUtils {
    private CategoryTreeUtils() {
    }

    static Category getCategoryOrThrow(final Identifiable<Category> category, final CategoryTree categoryTree) {
        return categoryTree.findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException(format("%s is not part of the category tree", category)));
    }
}
