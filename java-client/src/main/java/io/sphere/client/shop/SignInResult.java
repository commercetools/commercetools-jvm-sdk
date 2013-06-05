package io.sphere.client.shop;

import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;

/** Result object returned by {@link io.sphere.client.shop.SphereClient SphereClient's} sign-in and sign-up methods.
 *  Contains a customer and their active cart (if such a cart exists, null otherwise). */
public class SignInResult {
    private Customer customer;
    private Cart cart;

    // for JSON deserializer
    private SignInResult() { }

    public SignInResult(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public Customer getCustomer() { return customer; }
    public Cart getCart() { return cart; }
}
