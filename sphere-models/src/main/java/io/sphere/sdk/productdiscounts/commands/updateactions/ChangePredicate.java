package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#changePredicate()}
 */
public class ChangePredicate extends UpdateAction<ProductDiscount> {
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
