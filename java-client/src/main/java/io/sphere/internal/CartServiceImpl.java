package io.sphere.internal;

import java.util.Currency;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.AuthenticatedCustomerResult;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.CartUpdate;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;
import io.sphere.internal.command.CartCommands;
import io.sphere.internal.command.Command;
import io.sphere.internal.request.RequestFactory;

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
        return requestFactory.createFetchRequest(
                endpoints.carts.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public FetchRequest<Cart> byCustomer(String customerId) {
        return requestFactory.createFetchRequest(
                endpoints.carts.byCustomer(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<Cart>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Cart> all() {
        return requestFactory.createQueryRequest(
                endpoints.carts.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Cart>>() {});
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
    public CommandRequest<Cart> updateCart(String cartId, int cartVersion, CartUpdate update) {
        return createCommandRequest(
                endpoints.carts.byId(cartId),
                update.createCommand(cartVersion));
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
