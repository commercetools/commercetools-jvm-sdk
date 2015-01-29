package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.ProductUpdateScope;

/**
 * Adds a product to a category.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#addToCategory()}
 */
public class AddToCategory extends StageableProductUpdateAction {
    private final Reference<Category> category;

    private AddToCategory(final Reference<Category> category, final ProductUpdateScope productUpdateScope) {
        super("addToCategory", productUpdateScope);
        this.category = category;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public static AddToCategory of(final Referenceable<Category> category, final ProductUpdateScope productUpdateScope) {
        return new AddToCategory(category.toReference(), productUpdateScope);
    }
}
