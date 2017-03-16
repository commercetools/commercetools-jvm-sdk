package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Removes a product from a category.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addToCategory()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddToCategory
 */
public final class RemoveFromCategory extends StagedProductUpdateActionImpl<Product> {
    private final Reference<Category> category;

    private RemoveFromCategory(final Reference<Category> category, @Nullable final Boolean staged) {
        super("removeFromCategory", staged);
        this.category = category;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public static RemoveFromCategory of(final Referenceable<Category> category) {
        return of(category, null);
    }

    public static RemoveFromCategory of(final Referenceable<Category> category, @Nullable final Boolean staged) {
        return new RemoveFromCategory(category.toReference(), staged);
    }
}
