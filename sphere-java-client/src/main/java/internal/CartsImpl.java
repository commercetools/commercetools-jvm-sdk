package de.commercetools.internal;

import java.util.Currency;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;

import com.google.common.base.Charsets;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class CartsImpl implements Carts {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartsImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    public RequestBuilder<Cart> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.carts(id), new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Cart>> all() {
        return requestFactory.createQueryRequest(endpoints.carts(), new TypeReference<QueryResult<Cart>>() {});
    }

    // ------------------------------------------------------
    // Async versions
    // ------------------------------------------------------

    /** Creates a cart on the backend asynchronously (does not block any thread by waiting for the response). */
    public ListenableFuture<Cart> createCartAsync(Currency currency, String customerId) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.createCart(), new CartCommands.CreateCart(currency, customerId)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> addLineItemAsync(String cartId, String cartVersion, String productId, int quantity) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.addLineItem(), new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> removeLineItemAsync(String cartId, String cartVersion, String lineItemId) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.removeLineItem(), new CartCommands.RemoveLineItem(cartId, cartVersion, lineItemId)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> updateLineItemQuantityAsync(String cartId, String cartVersion, String lineItemId, int quantity) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.updateLineItemQuantity(), new CartCommands.UpdateLineItemQuantity(cartId, cartVersion, lineItemId, quantity)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> setCustomerAsync(String cartId, String cartVersion, String customerId) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.setCustomer(), new CartCommands.SetCustomer(cartId, cartVersion, customerId)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> setShippingAddressAsync(String cartId, String cartVersion, String address) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.setShippingAddress(), new CartCommands.SetShippingAddress(cartId, cartVersion, address)),
                new TypeReference<Cart>() {});
    }


//    /** {@inheritDoc}  */
//    public ListenableFuture<Order> orderAsync(String cartId, String cartVersion) {
//        return RequestHolders.execute(
//                requestFactory.<Cart>createCommandRequest(
//                        endpoints.setShippingAddress(), new CartCommands.OrderCart(cartId, cartVersion)),
//                new TypeReference<Order>() {});
//    }
    // ------------------------------------------------------
    // Sync versions (just call async and wait for result)
    // ------------------------------------------------------

    /** Creates a cart on the backend. */
    public Cart createCart(Currency currency, String customerId) {
        try { return createCartAsync(currency, customerId).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public Cart addLineItem(String cartId, String cartVersion, String productId, int quantity) {
        try { return addLineItemAsync(cartId, cartVersion, productId, quantity).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public Cart removeLineItem(String cartId, String cartVersion, String lineItemId) {
        try { return removeLineItemAsync(cartId, cartVersion, lineItemId).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public Cart updateLineItemQuantity(String cartId, String cartVersion, String lineItemId, int quantity) {
        try { return updateLineItemQuantityAsync(cartId, cartVersion, lineItemId, quantity).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public Cart setCustomer(String cartId, String cartVersion, String customerId) {
        try { return setCustomerAsync(cartId, cartVersion, customerId).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** {@inheritDoc}  */
    public Cart setShippingAddress(String cartId, String cartVersion, String address) {
        try { return setShippingAddressAsync(cartId, cartVersion, address).get(); } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

//    /** {@inheritDoc}  */
//    public Order order(String cartId, String cartVersion) {
//        try { return orderAsync(cartId, cartVersion).get(); } catch(Exception ex) {
//            throw new BackendException(ex);
//        }
//    }
}
