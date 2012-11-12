package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Customer;

/**
 * Login result object, containing the logged in customer and his active cart (if such a cart exists, null otherwise).
 */
public class LoginResult {
    private Customer customer;
    private Cart cart;

    // for JSON deserializer
    private LoginResult() { }

    public LoginResult(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }
}
