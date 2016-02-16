package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;

/**
 * Changes the predicate of a discount.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changePredicate()}
 */
public final class ChangePredicate extends UpdateActionImpl<ProductDiscount> {
    private final String predicate;

    private ChangePredicate(final ProductDiscountPredicate predicate) {
        super("changePredicate");
        this.predicate = predicate.toSpherePredicate();
    }

    public static ChangePredicate of(final ProductDiscountPredicate predicate) {
        return new ChangePredicate(predicate);
    }

    public String getPredicate() {
        return predicate;
    }
}
