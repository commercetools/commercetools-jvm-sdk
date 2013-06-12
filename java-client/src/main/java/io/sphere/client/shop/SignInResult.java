package io.sphere.client.shop;

import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Customer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Result returned by {@link io.sphere.client.shop.SphereClient SphereClient's} sign-in and sign-up methods.
 *  Contains a customer and their active cart (if such cart exists, null otherwise). */
public class SignInResult {
    private Customer customer;
    private Cart cart;

    // for JSON deserializer
    private SignInResult() { }

    /** The existing customer. */
    @Nonnull public Customer getCustomer() { return customer; }
    /** Customer's cart or null if no cart exists. */
    @Nullable public Cart getCart() { return cart; }
}
