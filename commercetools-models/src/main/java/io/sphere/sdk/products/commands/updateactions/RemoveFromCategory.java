package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
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
    private final ResourceIdentifier<Category> category;

    private RemoveFromCategory(final ResourceIdentifier<Category> category, @Nullable final Boolean staged) {
        super("removeFromCategory", staged);
        this.category = category;
    }

    public ResourceIdentifier<Category> getCategory() {
        return category;
    }

    public static RemoveFromCategory of(final Referenceable<Category> category) {
        return of(category, null);
    }

    public static RemoveFromCategory of(final Referenceable<Category> category, @Nullable final Boolean staged) {
        return new RemoveFromCategory(category.toReference(), staged);
    }

    public static RemoveFromCategory of(final ResourceIdentifier<Category> category) {
        return of(category, null);
    }

    public static RemoveFromCategory of(final ResourceIdentifier<Category> category, @Nullable final Boolean staged) {
        return new RemoveFromCategory(category, staged);
    }
}
