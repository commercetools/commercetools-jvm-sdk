package sphere;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.ShopClient;
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
            Log.debug("[cart] Found whole cart in session: " + IdWithVersion.string(cart.getId(), cart.getVersion()));
            return cart;
        }
        IdWithVersion cartId = tryGetCartIdFromSession();
        if (cartId != null) {
            Log.debug("[cart] Found cart id in session, fetching cart from backend: " + cartId);
            return cartService.byId(cartId.getId()).fetch();
        } else {
            Log.debug("[cart] No cart info in session, returning an empty dummy cart.");
            return Cart.empty(); // don't create cart on the backend immediately
        }
    }

    // --------------------------------------
    // Commands
    // --------------------------------------

    public Cart addLineItem(String productId) {
        return addLineItem(productId, 1);
    }

    public Cart addLineItem(String productId, int quantity) {
        IdWithVersion cartId = ensureCart();
        Log.debug(String.format("[cart] Adding product %s to cart %s", productId, cartId));
        return putCartToSession(cartService.addLineItem(cartId.getId(), cartId.getVersion(), productId, quantity).execute());
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId) {
        return addLineItemAsync(productId, 1);
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        IdWithVersion cartId = ensureCart();
        Log.debug(String.format("[cart] Adding product %s to cart %s", productId, cartId));
        return Futures.transform(cartService.addLineItem(cartId.getId(), cartId.getVersion(), productId, quantity).executeAsync(), new Function<Cart, Cart>() {
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
            Log.debug("[cart] Creating a new cart on the backend and associating it with current session.");
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
            Log.warn(String.format("[cart] Session value %s, %s.", key, json));
            return jsonParser.readValue(json, jsonParserTypeRef);
        } catch (IOException e) {
            Log.error("Cannot parse " + key + " from session", e);
            return null;
        }
    }
    private <T> void putToSession(Http.Session session, String key, T obj) {
        ObjectWriter jsonWriter = new ObjectMapper().writer();
        Log.warn(String.format("[cart] Putting to session: key %s, value %s.", key, obj));
        try {
            session.put(key, jsonWriter.writeValueAsString(obj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}