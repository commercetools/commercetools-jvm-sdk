package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.carts.Cart;

import java.util.Optional;

@JsonDeserialize(as = CustomerSignInResultImpl.class)
public interface CustomerSignInResult {
    Customer getCustomer();

    Optional<Cart> getCart();

    static TypeReference<CustomerSignInResult> typeReference() {
        return new TypeReference<CustomerSignInResult>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerSignInResult>";
            }
        };
    }
}
