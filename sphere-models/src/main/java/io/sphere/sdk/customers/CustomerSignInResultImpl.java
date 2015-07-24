package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;

class CustomerSignInResultImpl extends Base implements CustomerSignInResult {
    private final Customer customer;
    private final Cart cart;

    @JsonCreator
    CustomerSignInResultImpl(final Customer customer, final Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Cart getCart() {
        return cart;
    }
}
