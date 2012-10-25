package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {
    /** Creates a request builder that finds a cart by given id. */
    RequestBuilder<Cart> byId(String id);

    /** Creates a request builder that queries all carts. */
    RequestBuilder<QueryResult<Cart>> all();

    /** Creates a request builder that queries all carts of the given customer. */
    public RequestBuilder<QueryResult<Cart>> byCustomerId(String customerId);

    /** Creates a cart on the backend. */
    CommandRequestBuilder<Cart> createCart(Currency currency);

    /** Creates a cart on the backend. */
    CommandRequestBuilder<Cart> createCart(Currency currency, String customerId);

    /** Adds a product to given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> addLineItem(String cartId, int cartVersion, String productId, int quantity);

    /** Removes a line item from given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId);

    /** Updates quantity of given line item in given cart and returns the updated Cart. */
    CommandRequestBuilder<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity);

    /** Increases the quantity of the given line item in given cart and returns the updated Cart. */
    CommandRequestBuilder<Cart> increaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityAdded);

    /** Decreases the quantity of the given line item in given cart and returns the updated Cart.
     * If quantityRemoved is greater than the line item quantity in the cart, the new quantity is set to 0. */
    CommandRequestBuilder<Cart> decreaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityRemoved);

    /** Sets a customer of given cart and returns the updated Cart.. */
    CommandRequestBuilder<Cart> setCustomer(String cartId, int cartVersion, String customerId);

    /** Sets shipping address of given cart and returns the updated Cart. */

    CommandRequestBuilder<Cart> setShippingAddress(String cartId, int cartVersion, String address);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequestBuilder<Order> order(String cartId, int cartVersion);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequestBuilder<Order> order(String cartId, int cartVersion, PaymentState paymentState);

}
