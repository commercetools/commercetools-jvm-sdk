package de.commercetools.sphere.client.shop;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.util.RequestBuilder;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {

    /** Creates a request builder that finds a cart by given id. */
    RequestBuilder<Cart> byId(String id);

    // -------------------------------------
    // Async versions
    // -------------------------------------

    /** Creates a cart on the backend asynchronously (does not block any thread by waiting for the response). */
    ListenableFuture<Cart> createCartAsync(Currency currency, String customerId);

    /** Adds a line item into given cart asynchronously (does not block any thread by waiting for the response). */
    ListenableFuture<Cart> addLineItemAsync(String cartId, String cartVersion, String productId, int quantity);

    // -------------------------------------
    // Sync versions
    // -------------------------------------

    /** Creates a cart on the backend. */
    Cart createCart(Currency currency, String customerId);

    /** Adds a line into given cart and returns the updated Cart. */
    Cart addLineItem(String cartId, String cartVersion, String productId, int quantity);
}
