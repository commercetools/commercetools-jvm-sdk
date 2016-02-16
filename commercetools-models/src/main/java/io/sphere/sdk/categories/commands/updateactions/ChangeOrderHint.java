package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the order hint of a category.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#changeOrderHint()}
 */
public final class ChangeOrderHint extends UpdateActionImpl<Category> {
    private final String orderHint;

    private ChangeOrderHint(final String orderHint) {
        super("changeOrderHint");
        this.orderHint = orderHint;
    }

    public static ChangeOrderHint of(final String orderHint) {
        return new ChangeOrderHint(orderHint);
    }

    public String getOrderHint() {
        return orderHint;
    }
}
