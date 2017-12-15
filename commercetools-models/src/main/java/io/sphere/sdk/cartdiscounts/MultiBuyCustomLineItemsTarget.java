package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * The multi-buy discount represents a "Buy 4 items, get 2 of them at a discounted rate" type of discount. This discount target is very similar
 * to {@link MultiBuyLineItemsTarget} but applied on custom line items instead of line items.
 */
public final class MultiBuyCustomLineItemsTarget extends AbstractMultiBuyTarget {

    @JsonCreator
    MultiBuyCustomLineItemsTarget(final Long discountedQuantity, @Nullable final Long maxOccurrence,
                                  final String predicate, final SelectionMode selectionMode, final Long triggerQuantity) {
        super(discountedQuantity,maxOccurrence,predicate,selectionMode,triggerQuantity);
    }

    public static MultiBuyCustomLineItemsTarget of(final String predicate, final Long triggerQuantity,
                                             final Long discountedQuantity, final SelectionMode selectionMode) {
        return new MultiBuyCustomLineItemsTarget(discountedQuantity, null, predicate, selectionMode, triggerQuantity);
    }

    public static MultiBuyCustomLineItemsTarget of(final String predicate, final Long triggerQuantity,
                                             final Long discountedQuantity, final SelectionMode selectionMode,
                                             @Nullable final Long maxOccurrence) {
        return new MultiBuyCustomLineItemsTarget(discountedQuantity, maxOccurrence, predicate, selectionMode, triggerQuantity);
    }

}
