package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Sets/unsets the category order hint which can be used to sort products.
 *
 * {@doc.gen intro}
 *
 */
public final class SetCategoryOrderHint extends UpdateActionImpl<Product> {
    private final String categoryId;
    @Nullable
    private final String orderHint;

    private SetCategoryOrderHint(final String categoryId, @Nullable final String orderHint) {
        super("setCategoryOrderHint");
        this.categoryId = categoryId;
        this.orderHint = orderHint;
    }

    public static SetCategoryOrderHint of(final String categoryId, @Nullable final String orderHint) {
        return new SetCategoryOrderHint(categoryId, orderHint);
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Nullable
    public String getOrderHint() {
        return orderHint;
    }
}
