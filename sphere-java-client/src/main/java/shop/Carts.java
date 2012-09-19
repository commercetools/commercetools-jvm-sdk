package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.orders.*;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {
    /** Creates a request builder that finds a cart by given id. */
    RequestBuilder<Cart> byId(String id);

    /** Creates a request builder that queries all carts. */
    RequestBuilder<QueryResult<Cart>> all();

    /** Creates a cart on the backend. */
    CommandRequestBuilder<Cart> createCart(Currency currency, String customerId);

    /** Creates a cart on the backend. */
    CommandRequestBuilder<Cart> createCart(Currency currency);

    /** Adds a product to given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> addLineItem(String cartId, int cartVersion, String productId, int quantity);

    /** Removes a line item from given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId);

    /** Updates quantity of given line item in given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity);

    /** Sets a customer of given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> setCustomer(String cartId, int cartVersion, String customerId);

    /** Sets shipping address of given cart and returns the updated Cart.. */

    CommandRequestBuilder<Cart> setShippingAddress(String cartId, int cartVersion, String address);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequestBuilder<Order> order(String cartId, int cartVersion);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequestBuilder<Order> order(String cartId, int cartVersion, PaymentState paymentState);

}
