package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Collections;
import java.util.List;

/**
 * Changes the cart discounts.
 *
 * {@doc.gen intro}
 *
 *
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandIntegrationTest#changeCartDiscounts()}
 *
 * @see DiscountCode#getCartDiscounts()
 */
public final class ChangeCartDiscounts extends UpdateActionImpl<DiscountCode> {
    private final List<Reference<CartDiscount>> cartDiscounts;

    private ChangeCartDiscounts(final List<Reference<CartDiscount>> cartDiscounts) {
        super("changeCartDiscounts");
        this.cartDiscounts = cartDiscounts;
    }

    public static ChangeCartDiscounts of(final Referenceable<CartDiscount> cartDiscount) {
        return of(Collections.singletonList(cartDiscount.toReference()));
    }

    public static ChangeCartDiscounts of(final List<Reference<CartDiscount>> cartDiscounts) {
        return new ChangeCartDiscounts(cartDiscounts);
    }


    public List<Reference<CartDiscount>> getCartDiscounts() {
        return cartDiscounts;
    }
}
