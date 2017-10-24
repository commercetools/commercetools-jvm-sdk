package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * Multi-Buy discount represents a "Buy 6 items get 2 of them discounted" type of discount.
 * A multi-buy discount is not applied on line items but on the individual items that are contained in a line item and subsumed under the
 * quantity field of the line item. A multi-buy discount can be applied multiple times in a cart.
 */
public final class MultiBuyLineItemsTarget extends AbstractMultiBuyTarget {


    @JsonCreator
    MultiBuyLineItemsTarget(final Long discountedQuantity, @Nullable final Long maxOccurrence,
                                  final String predicate, final SelectionMode selectionMode, final Long triggerQuantity) {
        super(discountedQuantity,maxOccurrence,predicate,selectionMode,triggerQuantity);
    }

    public static MultiBuyLineItemsTarget of(final String predicate, final Long triggerQuantity,
                                                   final Long discountedQuantity, final SelectionMode selectionMode) {
        return new MultiBuyLineItemsTarget(discountedQuantity, null, predicate, selectionMode, triggerQuantity);
    }

    public static MultiBuyLineItemsTarget of(final String predicate, final Long triggerQuantity,
                                                   final Long discountedQuantity, final SelectionMode selectionMode,
                                                   @Nullable final Long maxOccurrence) {
        return new MultiBuyLineItemsTarget(discountedQuantity, maxOccurrence, predicate, selectionMode, triggerQuantity);
    }

}
