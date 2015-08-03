package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;

/**
 * Adds a product to a category.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addToCategory()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveFromCategory
 */
public class AddToCategory extends UpdateActionImpl<Product> {
    private final Reference<Category> category;

    private AddToCategory(final Reference<Category> category) {
        super("addToCategory");
        this.category = category;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public static AddToCategory of(final Referenceable<Category> category) {
        return new AddToCategory(category.toReference());
    }
}
