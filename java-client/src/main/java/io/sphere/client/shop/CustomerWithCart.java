package io.sphere.client.shop;

import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;

/** Result object returned by {@link io.sphere.client.shop.SphereClient SphereClient's} login and signup methods.
 *  Contains a customer and their active cart (if such a cart exists, null otherwise). */
public class CustomerWithCart {
    private Customer customer;
    private Cart cart;

    // for JSON deserializer
    private CustomerWithCart() { }

    public CustomerWithCart(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }

    public Customer getCustomer() { return customer; }
    public Cart getCart() { return cart; }
}
