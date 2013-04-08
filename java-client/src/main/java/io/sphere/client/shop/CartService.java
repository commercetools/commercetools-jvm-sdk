package io.sphere.client.shop;

import java.util.Currency;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.CartUpdate;
import io.sphere.client.shop.model.Order;
import io.sphere.client.shop.model.PaymentState;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface CartService {
    /** Creates a request that finds a cart by given id. */
    FetchRequest<Cart> byId(String id);

    /** Finds the active cart for given customer. */
    FetchRequest<Cart> byCustomer(String customerId);

    /** Queries all carts. */
    QueryRequest<Cart> all();

    /** Merges an anonymous cart with customer's active cart and returns the customer, including their cart.
     *  The returned command returns {@link Optional#absent} if customer with given credentials does not exist. */
    CommandRequest<Optional<AuthenticatedCustomerResult>> loginWithAnonymousCart(String cartId, int cartVersion, String email, String password);

    /** Creates a cart on the backend. */
    public CommandRequest<Cart> createCart(Currency currency, CountryCode country, Cart.InventoryMode inventoryMode);

    /** Creates a cart on the backend. */
    public CommandRequest<Cart> createCart(Currency currency, String customerId, Cart.InventoryMode inventoryMode);

    /** Creates a cart on the backend. */
    CommandRequest<Cart> createCart(Currency currency, String customerId,  CountryCode country, Cart.InventoryMode inventoryMode);

    /** Creates a cart on the backend. */
    public CommandRequest<Cart> createCart(Currency currency, Cart.InventoryMode inventoryMode);

    /** Updates a cart on the backend. */
    public CommandRequest<Cart> updateCart(String cartId, int cartVersion, CartUpdate update);

        /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequest<Order> createOrder(String cartId, int cartVersion);

    /** Creates an order from a cart. The cart object does not exist any more in the backend. */
    CommandRequest<Order> createOrder(String cartId, int cartVersion, PaymentState paymentState);

}
