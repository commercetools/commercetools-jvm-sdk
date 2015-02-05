package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Removes a product from a category.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addToCategory()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddToCategory
 */
public class RemoveFromCategory extends StageableProductUpdateAction {
    private final Reference<Category> category;

    private RemoveFromCategory(final Reference<Category> category, final ProductUpdateScope productUpdateScope) {
        super("removeFromCategory", productUpdateScope);
        this.category = category;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public static RemoveFromCategory of(final Referenceable<Category> category, final ProductUpdateScope productUpdateScope) {
        return new RemoveFromCategory(category.toReference(), productUpdateScope);
    }
}
