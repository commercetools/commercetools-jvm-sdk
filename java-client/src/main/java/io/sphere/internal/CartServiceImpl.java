package io.sphere.internal;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.AuthenticatedCustomerResult;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.model.Address;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.Command;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

import java.util.Currency;

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
    public CommandRequest<Cart> addLineItem(String cartId, int cartVersion, String productId, String variantId, int quantity, Reference catalog) {
        int vId;
        try {
            vId = Integer.parseInt(variantId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid variant id: '" + variantId + "'");
        }
        return createCommandRequest(
                endpoints.carts.addLineItem(),
                new CartCommands.AddLineItem(cartId, cartVersion, productId, quantity, vId, catalog));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> removeLineItem(String cartId, int cartVersion, String lineItemId) {
        return createCommandRequest(
                endpoints.carts.removeLineItem(),
                new CartCommands.RemoveLineItem(cartId, cartVersion, lineItemId));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> setShippingAddress(String cartId, int cartVersion, Address address) {
        return createCommandRequest(
                endpoints.carts.setShippingAddress(),
                new CartCommands.SetShippingAddress(cartId, cartVersion, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> setBillingAddress(String cartId, int cartVersion, Address address) {
        return createCommandRequest(
                endpoints.carts.setBillingAddress(),
                new CartCommands.SetBillingAddress(cartId, cartVersion, address));
    }
    /** {@inheritDoc}  */
    public CommandRequest<Cart> setCountry(String cartId, int cartVersion, CountryCode country) {
        return createCommandRequest(
                endpoints.carts.setCountry(),
                new CartCommands.ChangeCountry(cartId, cartVersion, country));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Cart> recalculatePrices(String cartId, int cartVersion) {
        return createCommandRequest(
                endpoints.carts.recalculate(),
                new CartCommands.RecalculateCartPrices(cartId, cartVersion));
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
