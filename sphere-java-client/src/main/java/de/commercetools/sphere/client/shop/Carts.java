package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.CommandRequest;

import java.util.Currency;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface Carts {
    /** Creates a request that finds a cart by given id. */
    QueryRequest<Cart> byId(String id);

    /** Creates a request that finds the active cart of the given customer */
    QueryRequest<Cart> byCustomer(String customerId);

    /** Creates a request that queries all carts. */
    QueryRequest<QueryResult<Cart>> all();

    /** Merges the anonymous cart with customers active cart and returns the customer with his cart. */
    CommandRequest<LoginResult> loginWithAnonymousCart(String cartId, int cartVersion, String email, String password);

    /** Creates a cart on the backend. */
    CommandRequest<Cart> createCart(Currency currency);

    /** Creates a cart on the backend. */
    CommandRequest<Cart> createCart(Currency currency, String customerId);

    /** Adds a product to given cart and returns the updated Cart.. */
    CommandRequest<Cart> addLineItem(String cartId, int cartVersion, String productId, int quantity);

    /** Removes a line item from given cart and returns the updated Cart.. */
    CommandRequest<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId);

    /** Updates quantity of given line item in given cart and returns the updated Cart. */
    CommandRequest<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity);

    /** Increases the quantity of the given line item in given cart and returns the updated Cart. */
    CommandRequest<Cart> increaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityAdded);

    /** Decreases the quantity of the given line item in given cart and returns the updated Cart.
     * If quantityRemoved is greater than the line item quantity in the cart, the new quantity is set to 0. */
    CommandRequest<Cart> decreaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityRemoved);

    /** Sets shipping address of given cart and returns the updated Cart. */
    CommandRequest<Cart> setShippingAddress(String cartId, int cartVersion, String address);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequest<Order> order(String cartId, int cartVersion);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequest<Order> order(String cartId, int cartVersion, PaymentState paymentState);

}
