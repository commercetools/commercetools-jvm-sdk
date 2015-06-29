package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.productdiscounts.ProductDiscount;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#changeSortOrder()}
 */
public class ChangeSortOrder extends UpdateAction<ProductDiscount> {
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
