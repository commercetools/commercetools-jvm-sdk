package de.commercetools.internal;

import java.util.Currency;

import de.commercetools.internal.command.CartCommands;
import de.commercetools.internal.command.Command;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.CommandRequest;

import org.codehaus.jackson.type.TypeReference;

public class CartsImpl implements Carts {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartsImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public QueryRequest<Cart> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.carts.byId(id), new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<QueryResult<Cart>> all() {
        return requestFactory.createQueryRequest(endpoints.carts.root(), new TypeReference<QueryResult<Cart>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<QueryResult<Cart>> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.carts.queryByCustomerId(customerId),
                new TypeReference<QueryResult<Cart>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Cart> createCommandRequest(String url, Command command) {
        return requestFactory.<Cart>createCommandRequest(url, command, new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, String customerId) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency) {
        return createCart(currency, null);
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> addLineItem(String cartId, int cartVersion, String productId, int quantity) {
        return createCommandRequest(
                endpoints.carts.addLineItem(),
                new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId) {
        return createCommandRequest(
                endpoints.carts.removeLineItem(),
                new CartCommands.RemoveLineItem(cartId, cartVersion, lineItemId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> updateLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantity) {
        return createCommandRequest(
                endpoints.carts.updateLineItemQuantity(),
                new CartCommands.UpdateLineItemQuantity(cartId, cartVersion, lineItemId, quantity));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> increaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityAdded) {
        return createCommandRequest(
                endpoints.carts.increaseLineItemQuantity(),
                new CartCommands.IncreaseLineItemQuantity(cartId, cartVersion, lineItemId, quantityAdded));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> decreaseLineItemQuantity(String cartId, int cartVersion, String lineItemId, int quantityRemoved) {
        return createCommandRequest(
                endpoints.carts.decreaseLineItemQuantity(),
                new CartCommands.DecreaseLineItemQuantity(cartId, cartVersion, lineItemId, quantityRemoved));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> setCustomer(String cartId, int cartVersion, String customerId) {
        return createCommandRequest(
                endpoints.carts.setCustomer(),
                new CartCommands.SetCustomer(cartId, cartVersion, customerId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> setShippingAddress(String cartId, int cartVersion, String address) {
        return createCommandRequest(
                endpoints.carts.setShippingAddress(),
                new CartCommands.SetShippingAddress(cartId, cartVersion, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> order(String cartId, int cartVersion, PaymentState paymentState) {
        return requestFactory.createCommandRequest(
                endpoints.carts.order(),
                new CartCommands.OrderCart(cartId, cartVersion, paymentState),
                new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> order(String cartId, int cartVersion) {
        return order(cartId, cartVersion, null);
    }

}
