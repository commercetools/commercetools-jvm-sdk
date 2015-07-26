package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 <p>Sets an existing customer ID for a cart.</p>
 The customer ID can be unset by calling {@link SetCustomerId#of(String)} with {@link java.util.Optional#empty()}.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setCustomerId()}
 */
public class SetCustomerId extends UpdateAction<Cart> {
    @Nullable
    private final String customerId;

    private SetCustomerId(@Nullable final String customerId) {
        super("setCustomerId");
        this.customerId = customerId;
    }

    public static SetCustomerId of(@Nullable final String customerId) {
        return new SetCustomerId(customerId);
    }

    public static SetCustomerId empty() {
        return new SetCustomerId(null);
    }


    public static SetCustomerId of(@Nullable final Referenceable<Customer> customer) {
        return of(Optional.ofNullable(customer).map(c -> c.toReference().getId()).orElse(null));
    }

    @Nullable
    public String getCustomerId() {
        return customerId;
    }
}
