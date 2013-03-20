package de.commercetools.internal;

import java.util.Currency;

import de.commercetools.internal.command.CartCommands;
import de.commercetools.internal.command.Command;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.AuthenticatedCustomerResult;
import de.commercetools.sphere.client.shop.CartService;
import de.commercetools.sphere.client.shop.model.Address;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Order;
import de.commercetools.sphere.client.shop.model.PaymentState;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.type.TypeReference;

public class CartServiceImpl implements CartService {
    private ProjectEndpoints endpoints;
    private RequestFactory requestFactory;

    public CartServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        this.requestFactory = requestFactory;
        this.endpoints = endpoints;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Cart> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.carts.byId(id), new TypeReference<Cart>() {
        });
    }

    /** {@inheritDoc}  */
    public FetchRequest<Cart> byCustomer(String customerId) {
        return requestFactory.createFetchRequest(endpoints.carts.byCustomer(customerId), new TypeReference<Cart>() {
        });
    }

    /** {@inheritDoc}  */
    public QueryRequest<Cart> all() {
        return requestFactory.createQueryRequest(endpoints.carts.root(), new TypeReference<QueryResult<Cart>>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Cart> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, String customerId, CountryCode country, Cart.InventoryMode inventoryMode) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId, country, inventoryMode));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, String customerId, Cart.InventoryMode inventoryMode) {
        return createCommandRequest(
                endpoints.carts.root(),
                new CartCommands.CreateCart(currency, customerId, null, inventoryMode));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, Cart.InventoryMode inventoryMode) {
        return createCart(currency, null, null, inventoryMode);
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> createCart(Currency currency, CountryCode country, Cart.InventoryMode inventoryMode) {
        return createCart(currency, null, country, inventoryMode);
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> addLineItem(String cartId, int cartVersion, String productId, int variantId, int quantity, Reference catalog) {
        return createCommandRequest(
                endpoints.carts.addLineItem(),
                new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity, variantId, catalog));
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
    public CommandRequest<Cart> setShippingAddress(String cartId, int cartVersion, Address address) {
        return createCommandRequest(
                endpoints.carts.setShippingAddress(),
                new CartCommands.SetShippingAddress(cartId, cartVersion, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Optional<AuthenticatedCustomerResult>> loginWithAnonymousCart(String cartId, int cartVersion, String email, String password) {
        return requestFactory.createCommandRequestWithErrorHandling(
                endpoints.carts.loginWithAnonymousCart(),
                new CartCommands.LoginWithAnonymousCart(cartId, cartVersion, email, password),
                401,
                new TypeReference<AuthenticatedCustomerResult>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> createOrder(String cartId, int cartVersion, PaymentState paymentState) {
        return requestFactory.createCommandRequest(
                endpoints.carts.order(),
                new CartCommands.OrderCart(cartId, cartVersion, paymentState),
                new TypeReference<Order>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Order> createOrder(String cartId, int cartVersion) {
        return createOrder(cartId, cartVersion, null);
    }
}
