package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;

/**
 * Adds a product to a category.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addToCategory()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.RemoveFromCategory
 * @see ProductProjection#getCategories()
 * @see io.sphere.sdk.products.ProductData#getCategories()
 */
public final class AddToCategory extends StagedProductUpdateActionImpl<Product> {
    private final ResourceIdentifier<Category> category;
    @Nullable
    private final String orderHint;

    private AddToCategory(final ResourceIdentifier<Category> category, @Nullable final String orderHint, @Nullable final Boolean staged) {
        super("addToCategory", staged);
        this.category = category;
        this.orderHint = orderHint;
    }

    public ResourceIdentifier<Category> getCategory() {
        return category;
    }

    public static AddToCategory of(final Referenceable<Category> category) {
        return of(category, null, null);
    }

    public static AddToCategory of(final Referenceable<Category> category, @Nullable final Boolean staged) {
        return new AddToCategory(category.toReference(), null, staged);
    }

    public static AddToCategory of(final Referenceable<Category> category, final String orderHint) {
        return of(category, orderHint, null);
    }

    public static AddToCategory of(final Referenceable<Category> category, final String orderHint, @Nullable final Boolean staged) {
        return new AddToCategory(category.toReference(), orderHint, staged);
    }

    public static AddToCategory of(final ResourceIdentifier<Category> category) {
        return of(category, null, null);
    }

    public static AddToCategory of(final ResourceIdentifier<Category> category, @Nullable final Boolean staged) {
        return of(category, null, staged);
    }

    public static AddToCategory of(final ResourceIdentifier<Category> category, final String orderHint) {
        return of(category, orderHint, null);
    }

    public static AddToCategory of(final ResourceIdentifier<Category> category, final String orderHint, @Nullable final Boolean staged) {
        return new AddToCategory(category, orderHint, staged);
    }

    @Nullable
    public String getOrderHint() {
        return orderHint;
    }
}
