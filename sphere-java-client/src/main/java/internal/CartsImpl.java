package de.commercetools.internal;

import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.Carts;
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
import java.util.Currency;

public class CartsImpl implements Carts {
    private ProjectEndpoints endpoints;
    private ClientCredentials credentials;
    private RequestFactory requestFactory;

    public CartsImpl(RequestFactory requestFactory, ProjectEndpoints endpoints, ClientCredentials credentials) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
        this.credentials = credentials;
    }

    public RequestBuilder<Cart> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.carts(id), credentials, new TypeReference<Cart>() {});
    }

    // ------------------------------------------------------
    // Async versions
    // ------------------------------------------------------

    /** Creates a cart on the backend asynchronously (does not block any thread by waiting for the response). */
    public ListenableFuture<Cart> createCartAsync(Currency currency, String customerId) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.createCart(), credentials, new CartCommands.CreateCart(currency, customerId)),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public ListenableFuture<Cart> addLineItemAsync(String cartId, String cartVersion, String productId, int quantity) {
        return RequestHolders.execute(
                requestFactory.<Cart>createCommandRequest(
                        endpoints.lineItems(), credentials, new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity)),
                new TypeReference<Cart>() {});
    }

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
}
