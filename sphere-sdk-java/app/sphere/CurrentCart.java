package sphere;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.ShopClient;
import de.commercetools.sphere.client.shop.model.Order;
import de.commercetools.sphere.client.shop.model.PaymentState;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.util.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import play.mvc.Http;
import sphere.util.IdWithVersion;
import net.jcip.annotations.ThreadSafe;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Currency;
import java.util.concurrent.ExecutionException;

/** Provides functionality for working with a shopping cart automatically associated to the current HTTP session. */
@ThreadSafe
public class CurrentCart {
    private final Http.Session session;
    private final Carts cartService;
    private Currency cartCurrency;
    private boolean storeFullCartInSession;

    public CurrentCart(Http.Session session, Carts cartService, Currency cartCurrency, boolean storeFullCartInSession) {
        this.session = session;
        this.cartService = cartService;
        this.cartCurrency = cartCurrency;
        this.storeFullCartInSession = storeFullCartInSession;
    }

    public Cart fetch() {
        // optimization: store the whole cart in session so we don't have to fetch from the backend?
        Cart cart = tryGetCartFromSession();
        if (cart != null) {
            Log.trace("[cart] Found whole cart in session: " + IdWithVersion.string(cart.getId(), cart.getVersion()));
            return cart;
        }
        IdWithVersion cartId = tryGetCartIdFromSession();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from backend: " + cartId);
            return cartService.byId(cartId.getId()).fetch();
        } else {
            Log.trace("[cart] No cart info in session, returning an empty dummy cart.");
            return Cart.empty(); // don't create cart on the backend immediately
        }
    }

    // --------------------------------------
    // Commands
    // --------------------------------------

    // AddLineItem --------------------------

    public Cart addLineItem(String productId) {
        return addLineItem(productId, 1);
    }

    public Cart addLineItem(String productId, int quantity) {
        try {
            return addLineItemAsync(productId, quantity).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId) {
        return addLineItemAsync(productId, 1);
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.addLineItem(cartId.getId(), cartId.getVersion(), productId, quantity),
                String.format("[cart] Adding product %s to cart %s.", productId, cartId));
    }

    // RemoveLineItem -----------------------

    public Cart removeLineItem(String lineItemId) {
        try {
            return removeLineItemAsync(lineItemId).get();
        } catch (Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> removeLineItemAsync(String lineItemId) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.removeLineItem(cartId.getId(), cartId.getVersion(), lineItemId),
                String.format("[cart] Removing line item %s from cart %s.", lineItemId, cartId));
    }

    // UpdateLineItemQuantity ---------------

    public Cart updateLineItemQuantity(String lineItemId, int quantity) {
        try {
            return updateLineItemQuantityAsync(lineItemId, quantity).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> updateLineItemQuantityAsync(String lineItemId, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.updateLineItemQuantity(cartId.getId(), cartId.getVersion(), lineItemId, quantity),
                String.format("[cart] Updating quantity of line item %s to %s in cart %s.", lineItemId, quantity, cartId));
    }

    // SetCustomer --------------------------

    public Cart setCustomer(String customerId) {
        try {
            return setCustomerAsync(customerId).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> setCustomerAsync(String customerId) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setCustomer(cartId.getId(), cartId.getVersion(), customerId),
                String.format("[cart] Setting customer %s for cart %s.", customerId, cartId));
    }

    // SetShippingAddress -------------------

    public Cart setShippingAddress(String address) {
        try {
            return setShippingAddressAsync(address).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> setShippingAddressAsync(String address) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setShippingAddress(cartId.getId(), cartId.getVersion(), address),
                String.format("[cart] Setting address for cart %s.", cartId));  // don't log personal data
    }

    // Order --------------------------------

    public Order order(PaymentState paymentState) {
        try {
            return orderAsync(paymentState).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Order> orderAsync(PaymentState paymentState) {
        IdWithVersion cartId = ensureCart();
        Log.trace(String.format("Ordering cart %s using payment state %s.", cartId, paymentState));
        return cartService.order(cartId.getId(), cartId.getVersion(), paymentState).executeAsync();
    }


    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Cart> executeAsync(CommandRequestBuilder<Cart> commandRequestBuilder, String logMessage) {
        IdWithVersion cartId = ensureCart();
        Log.trace(logMessage);
        return Futures.transform(commandRequestBuilder.executeAsync(), new Function<Cart, Cart>() {
            @Override
            public Cart apply(@Nullable Cart cart) {
                putCartToSession(cart);
                return cart;
            }
        });
    }

    // --------------------------------------
    // Ensure cart
    // --------------------------------------

    /** If a cart id is already in session, returns it. Otherwise creates a new cart on the backend. */
    private IdWithVersion ensureCart() {
        IdWithVersion cartId = tryGetCartIdFromSession();
        if (cartId == null) {
            Log.trace("[cart] Creating a new cart on the backend and associating it with current session.");
            Cart newCart = cartService.createCart(cartCurrency).execute();
            putCartToSession(newCart);
            cartId = new IdWithVersion(newCart.getId(), newCart.getVersion());
        }
        return cartId;
    }

    // --------------------------------------
    // Session helpers
    // --------------------------------------

    private String cartIdKey = "cartId";
    private String cartKey = "cart";

    private Cart tryGetCartFromSession() {
        return storeFullCartInSession ? tryGetFromSession(session, cartKey, new TypeReference<Cart>() {}) : null;
    }
    private Cart putCartToSession(Cart cart) {
        if (storeFullCartInSession) {
            putToSession(session, cartKey, cart);
        }
        putCartIdToSession(new IdWithVersion(cart.getId(), cart.getVersion()));
        return cart;
    }

    private IdWithVersion tryGetCartIdFromSession() {
        return tryGetFromSession(session, cartIdKey, new TypeReference<IdWithVersion>() {});
    }
    private void putCartIdToSession(IdWithVersion cartId) {
        putToSession(session, cartIdKey, cartId);
    }

    private <T> T tryGetFromSession(Http.Session session, String key, TypeReference<T> jsonParserTypeRef) {
        String json = session.get(key);
        if (json == null)
            return null;
        ObjectMapper jsonParser = new ObjectMapper();
        try {
            Log.trace(String.format("[cart] Session value %s, %s.", key, json));
            return jsonParser.readValue(json, jsonParserTypeRef);
        } catch (IOException e) {
            Log.error("Cannot parse " + key + " from session", e);
            return null;
        }
    }
    private <T> void putToSession(Http.Session session, String key, T obj) {
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        Log.trace(String.format("[cart] Putting to session: key %s, value %s.", key, obj));
        try {
            session.put(key, jsonWriter.writeValueAsString(obj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}