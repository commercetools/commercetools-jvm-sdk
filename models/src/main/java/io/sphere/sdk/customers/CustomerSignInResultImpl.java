package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;

import java.util.Optional;

class CustomerSignInResultImpl extends Base implements CustomerSignInResult {
    private final Customer customer;
    private final Optional<Cart> cart;

    @JsonCreator
    CustomerSignInResultImpl(final Customer customer, final Optional<Cart> cart) {
        this.customer = customer;
        this.cart = cart;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Optional<Cart> getCart() {
        return cart;
    }
}
