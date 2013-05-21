package io.sphere.client.shop;

import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;

/** Result object returned by {@link io.sphere.client.shop.SphereClient SphereClient's} login and signup methods.
 *  Contains a customer and their active cart (if such a cart exists, null otherwise). */
public class AuthenticatedCustomerResult {
    private Customer customer;
    private Cart cart;

    // for JSON deserializer
    private AuthenticatedCustomerResult() { }

    public AuthenticatedCustomerResult(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public Customer getCustomer() { return customer; }
    public Cart getCart() { return cart; }
}
