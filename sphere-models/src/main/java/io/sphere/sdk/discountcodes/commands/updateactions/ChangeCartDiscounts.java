package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#changeCartDiscounts()}
 */
public class ChangeCartDiscounts extends UpdateAction<DiscountCode> {
    private final List<Reference<CartDiscount>> cartDiscounts;

    private ChangeCartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        super("changeCartDiscounts");
        this.cartDiscounts = cartDiscounts;
    }

    public static ChangeCartDiscounts of(final Referenceable<CartDiscount> cartDiscount) {
        return of(asList(cartDiscount.toReference()));
    }

    public static ChangeCartDiscounts of(final List<Reference<CartDiscount>> cartDiscounts) {
        return new ChangeCartDiscounts(cartDiscounts);
    }


    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }
}
