package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * Multi-Buy discount represents a "Buy 6 items get 2 of them discounted" type of discount.
 * A multi-buy discount is not applied on line items but on the individual items that are contained in a line item and subsumed under the
 * quantity field of the line item. A multi-buy discount can be applied multiple times in a cart.
 */
public final class MultiBuyLineItemsTarget extends Base implements CartDiscountTarget {
    private Long discountedQuantity;

    @Nullable
    private Long maxOccurrence;

    private String predicate;

    private SelectionMode selectionMode;

    private Long triggerQuantity;

    @JsonCreator
    MultiBuyLineItemsTarget(final Long discountedQuantity, @Nullable final Long maxOccurrence,
                            final String predicate, final SelectionMode selectionMode, final Long triggerQuantity) {
        this.discountedQuantity = discountedQuantity;
        this.maxOccurrence = maxOccurrence;
        this.predicate = predicate;
        this.selectionMode = selectionMode;
        this.triggerQuantity = triggerQuantity;
    }

    /**
     * A valid line item target predicate. The discount will be applied to line items that are matched by the predicate.
     *
     * @return the line item target predicate
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     * Quantity of line items that need to be present in order to trigger an application of this discount.
     *
     * @return the quantity of line items to trigger the discount
     */
    public Long getTriggerQuantity() {
        return triggerQuantity;
    }

    /**
     * Quantity of line items that are discounted per application of this discount.
     *
     * @return the quantity of line items to discount
     */
    public Long getDiscountedQuantity() {
        return discountedQuantity;
    }

    /**
     * Maximum number of applications of this discount.
     *
     * @return the maximum number of applications
     */
    @Nullable
    public Long getMaxOccurrence() {
        return maxOccurrence;
    }

    /**
     * The selection mode.
     *
     * @return the selection mode
     */
    public SelectionMode getSelectionMode() {
        return selectionMode;
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
