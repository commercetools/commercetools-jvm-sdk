package io.sphere.client.shop;

import java.util.Currency;
import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Cart;
import io.sphere.client.shop.model.CartUpdate;
import com.neovisionaries.i18n.CountryCode;

/** Sphere HTTP API for working with shopping carts in a given project. */
public interface CartService {
    /** Creates a request that finds a cart by given id. */
    FetchRequest<Cart> byId(String id);

    /** Finds the active cart for given customer. */
    FetchRequest<Cart> byCustomer(String customerId);

    /** Queries all carts. */
    QueryRequest<Cart> all();

    /** Creates a cart in the backend. */
    public CommandRequest<Cart> createCart(Currency currency, String customerId, Cart.InventoryMode inventoryMode);

    /** Creates a cart in the backend. */
    CommandRequest<Cart> createCart(Currency currency, String customerId, CountryCode country, Cart.InventoryMode inventoryMode);

    /** Creates an anonymous cart in the backend. */
    public CommandRequest<Cart> createCart(Currency currency, CountryCode country, Cart.InventoryMode inventoryMode);

    /** Creates an anonymous cart in the backend. */
    public CommandRequest<Cart> createCart(Currency currency, Cart.InventoryMode inventoryMode);

    /** Updates a cart in the backend. */
    public CommandRequest<Cart> updateCart(VersionedId cartId, CartUpdate update);

}
