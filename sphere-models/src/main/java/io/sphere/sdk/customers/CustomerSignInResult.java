package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.Cart;

import javax.annotation.Nullable;

/**
 * A return type to contain a customer and the corresponding cart if there is any.
 *
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
 */
@JsonDeserialize(as = CustomerSignInResultImpl.class)
public interface CustomerSignInResult {
    Customer getCustomer();

    /**
     * A cart belonging to the customer or null.
     * @return cart or null
     */
    @Nullable
    Cart getCart();

    static TypeReference<CustomerSignInResult> typeReference() {
        return new TypeReference<CustomerSignInResult>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerSignInResult>";
            }
        };
    }
}
