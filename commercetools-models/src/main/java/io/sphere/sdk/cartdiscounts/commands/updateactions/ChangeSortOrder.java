package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the sort order.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandIntegrationTest#changeSortOrder()}
 */
public final class ChangeSortOrder extends UpdateActionImpl<CartDiscount> {
    private final String sortOrder;

    private ChangeSortOrder(final String sortOrder) {
        super("changeSortOrder");
        this.sortOrder = sortOrder;
    }

    public static ChangeSortOrder of(final String sortOrder) {
        return new ChangeSortOrder(sortOrder);
    }

    public String getSortOrder() {
        return sortOrder;
    }
}
