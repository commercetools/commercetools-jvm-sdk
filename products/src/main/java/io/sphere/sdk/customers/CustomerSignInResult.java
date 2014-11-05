package io.sphere.sdk.customers;

import io.sphere.sdk.carts.Cart;

import java.util.Optional;

public interface CustomerSignInResult {
    Customer getCustomer();

    Optional<Cart> getCart();
}
