package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 * Adds a product to a category.
 *
 * {@include.example products.ProductCrudIntegrationTest#addToCategoryUpdateAction()}
 */
public class AddToCategory extends StageableProductUpdateAction {
    private final Reference<Category> category;

    private AddToCategory(final Reference<Category> category, final boolean staged) {
        super("addToCategory", staged);
        this.category = category;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public static AddToCategory of(final Referenceable<Category> category, final boolean staged) {
        return new AddToCategory(category.toReference(), staged);
    }

    public static AddToCategory of(final Referenceable<Category> category) {
        return of(category, true);
    }
}
