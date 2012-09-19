package de.commercetools.internal;


import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import java.util.Currency;

public class CartsImpl implements Carts {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartsImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Cart> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.carts(id), new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Cart>> all() {
        return requestFactory.createQueryRequest(endpoints.carts(), new TypeReference<QueryResult<Cart>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequestBuilder<Cart> createCommandRequest(String url, Command command) {
        return requestFactory.<Cart>createCommandRequest(url, command, new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> createCart(Currency currency, String customerId) {
        return createCommandRequest(
                endpoints.createCart(),
                new CartCommands.CreateCart(currency, customerId));
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> createCart(Currency currency) {
        return createCart(currency, null);
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> addLineItem(String cartId, int cartVersion, String productId, int quantity) {
        return createCommandRequest(
                endpoints.addLineItem(),
                new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity));
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId) {
        return createCommandRequest(
                endpoints.removeLineItem(),
                new CartCommands.RemoveLineItem(cartId, cartVersion, lineItemId));
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity) {
        return createCommandRequest(
                endpoints.updateLineItemQuantity(),
                new CartCommands.UpdateLineItemQuantity(cartId, cartVersion, lineItemId, quantity));
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> setCustomer(String cartId, int cartVersion, String customerId) {
        return createCommandRequest(
                endpoints.setCustomer(),
                new CartCommands.SetCustomer(cartId, cartVersion, customerId));
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Cart> setShippingAddress(String cartId, int cartVersion, String address) {
        return createCommandRequest(
                endpoints.setShippingAddress(),
                new CartCommands.SetShippingAddress(cartId, cartVersion, address));
    }

    /** {@inheritDoc}  */
//    public CommandRequestBuilder<Order> order(String cartId, String cartVersion) {
//    }
}
