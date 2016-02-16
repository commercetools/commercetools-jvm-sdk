package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;

/**
 * Changes the sort order of a discount.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changeSortOrder()}
 */
public final class ChangeSortOrder extends UpdateActionImpl<ProductDiscount> {
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
