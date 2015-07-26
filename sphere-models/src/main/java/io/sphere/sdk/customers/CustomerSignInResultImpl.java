package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

class CustomerSignInResultImpl extends Base implements CustomerSignInResult {
    private final Customer customer;
    @Nullable
    private final Cart cart;

    @JsonCreator
    CustomerSignInResultImpl(final Customer customer, @Nullable final Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    @Nullable
    public Cart getCart() {
        return cart;
    }
}
