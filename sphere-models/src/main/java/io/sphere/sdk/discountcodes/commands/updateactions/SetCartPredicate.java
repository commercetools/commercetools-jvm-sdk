package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setCartPredicate()}
 */
public class SetCartPredicate extends UpdateAction<DiscountCode> {
    @Nullable
    private final String cartPredicate;

    private SetCartPredicate(@Nullable final String cartPredicate) {
        super("setCartPredicate");
        this.cartPredicate = cartPredicate;
    }

    public static SetCartPredicate of(@Nullable final CartPredicate cartPredicate) {
        return new SetCartPredicate(Optional.ofNullable(cartPredicate).map(CartPredicate::toSphereCartPredicate).orElse(null));
    }

    @Nullable
    public String getCartPredicate() {
        return cartPredicate;
    }
}
