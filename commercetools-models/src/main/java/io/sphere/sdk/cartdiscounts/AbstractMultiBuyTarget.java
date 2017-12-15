package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;


abstract class AbstractMultiBuyTarget extends Base implements CartDiscountTarget {
    private Long discountedQuantity;

    @Nullable
    private final Long maxOccurrence;

    private final String predicate;

    private final SelectionMode selectionMode;

    private final Long triggerQuantity;

    @JsonCreator
    AbstractMultiBuyTarget(final Long discountedQuantity, @Nullable final Long maxOccurrence,
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

}
