package de.commercetools.sphere.client.shop;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {

    /** Creates a request builder that finds a cart by given id. */
    RequestBuilder<Cart> byId(String id);

    /** Creates a request builder that queries all products. */
    RequestBuilder<QueryResult<Cart>> all();

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

    /** Removes a line item in the cart and returns the updated Cart. */
    Cart removeLineItem(String cartId, String cartVersion, String lineItemId);

    /** Updates the quantity of a specific line item and returns the updated Cart.  */
    Cart updateLineItemQuantity(String cartId, String cartVersion, String lineItemId, int quantity);

    /** Sets the customer id and returns the updated Cart.  */
    Cart setCustomer(String cartId, String cartVersion, String customerId);

    /** Sets the shipping address and returns the updated Cart. */
    Cart setShippingAddress(String cartId, String cartVersion, String address);

}
