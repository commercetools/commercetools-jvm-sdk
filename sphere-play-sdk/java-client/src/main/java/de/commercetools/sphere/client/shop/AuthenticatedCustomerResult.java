package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Customer;

/** Result object returned by {@link de.commercetools.sphere.client.SphereClient SphereClient's} login and signup methods.
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
