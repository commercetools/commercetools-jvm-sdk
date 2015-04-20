package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

/**
 <p>Sets an existing customer ID for a cart.</p>
 The customer ID can be unset by calling {@link io.sphere.sdk.carts.commands.updateactions.SetCustomerId#of(java.util.Optional)} with {@link java.util.Optional#empty()}.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setCustomerId()}
 */
public class SetCustomerId extends UpdateAction<Cart> {
    private final Optional<String> customerId;

    private SetCustomerId(final Optional<String> customerId) {
        super("setCustomerId");
        this.customerId = customerId;
    }

    public static SetCustomerId of(final Optional<String> customerId) {
        return new SetCustomerId(customerId);
    }

    public static SetCustomerId of(final String customerId) {
        return of(Optional.of(customerId));
    }

    public static SetCustomerId of(final Referenceable<Customer> customer) {
        return of(Optional.of(customer.toReference().getId()));
    }

    public Optional<String> getCustomerId() {
        return customerId;
    }
}
