package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets/unsets the category order hint which can be used to sort products.
 *
 * {@doc.gen intro}
 */
public final class SetCategoryOrderHint extends StagedProductUpdateActionImpl<Product> {
    private final String categoryId;
    @Nullable
    private final String orderHint;

    private SetCategoryOrderHint(final String categoryId, @Nullable final String orderHint, @Nullable final Boolean staged) {
        super("setCategoryOrderHint", staged);
        this.categoryId = categoryId;
        this.orderHint = orderHint;
    }

    public static SetCategoryOrderHint of(final String categoryId, @Nullable final String orderHint) {
        return of(categoryId, orderHint, null);
    }

    public static SetCategoryOrderHint of(final String categoryId, @Nullable final String orderHint, @Nullable final Boolean staged) {
        return new SetCategoryOrderHint(categoryId, orderHint, staged);
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Nullable
    public String getOrderHint() {
        return orderHint;
    }
}
