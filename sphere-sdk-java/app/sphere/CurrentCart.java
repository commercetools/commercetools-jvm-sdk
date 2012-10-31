package sphere;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.Carts;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Order;
import de.commercetools.sphere.client.shop.model.PaymentState;
import de.commercetools.internal.util.Log;
import play.mvc.Http;
import sphere.util.IdWithVersion;
import net.jcip.annotations.ThreadSafe;
import sphere.util.SessionUtil;

import javax.annotation.Nullable;
import java.util.Currency;

/** Provides functionality for working with a shopping cart automatically associated to the current HTTP session. */
@ThreadSafe
public class CurrentCart {
    private final Http.Session session;
    private final Carts cartService;
    private Currency cartCurrency;

    public CurrentCart(Http.Session session, Carts cartService, Currency cartCurrency) {
        this.session = session;
        this.cartService = cartService;
        this.cartCurrency = cartCurrency;
    }

    /** Fetches the cart object for the current user from the backend.
     *
     *  As an optimization, the cart is only created on the backend when user puts the first product into the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to the backend. */
    public Cart fetch() {
        IdWithVersion cartId = getCartIdFromSession();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from backend: " + cartId);
            return cartService.byId(cartId.id()).fetch();
        } else {
            Log.trace("[cart] No cart id in session, returning an empty dummy cart.");
            // Don't create cart on the backend immediately (do it only when the customer adds a product to the cart)
            return Cart.createEmpty(this.cartCurrency);
        }
    }

    /** Returns the number of items in the cart for current user.
     *
     *  This method is purely an optimization that lets you avoid using {@link #fetch()} and then calling {@link Cart#getNumberOfItems}
     *  if the only information you need to display is the number of items in the cart.
     *  The number is stored in {@link play.mvc.Http.Session} and updated on all cart modifications. */
    public int getTotalQuantity() {
        Integer cachedInSession = getCartTotalQuantityFromSession();
        Log.trace("[cart] CurrentCart.getTotalQuantity() = " + cachedInSession + " (from session).");
        return cachedInSession == null ? 0 : cachedInSession.intValue();
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
                cartService.addLineItem(cartId.id(), cartId.version(), productId, quantity),
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
                cartService.removeLineItem(cartId.id(), cartId.version(), lineItemId),
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
                cartService.updateLineItemQuantity(cartId.id(), cartId.version(), lineItemId, quantity),
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
                cartService.setCustomer(cartId.id(), cartId.version(), customerId),
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
                cartService.setShippingAddress(cartId.id(), cartId.version(), address),
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
        return Futures.transform(cartService.order(cartId.id(), cartId.version(), paymentState).executeAsync(), new Function<Order, Order>() {
            @Override
            public Order apply(@Nullable Order order) {
                clearCartInSession();  // cart does not exist anymore
                return order;
            }
        });
    }

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Cart> executeAsync(CommandRequest<Cart> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Futures.transform(commandRequest.executeAsync(), new Function<Cart, Cart>() {
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
        IdWithVersion cartId = getCartIdFromSession();
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

    private String cartIdKey = "ct-id";
    private String cartVersionKey = "ct-v";
    private String cartQuantityKey = "ct-q";

    private IdWithVersion createCartId(Cart cart) {
        return new IdWithVersion(cart.getId(), cart.getVersion());
    }

    private IdWithVersion getCartIdFromSession() {
        return SessionUtil.getIdOrNull(session, cartIdKey, cartVersionKey);
    }
    private void putCartToSession(Cart cart) {
        SessionUtil.putId(session, createCartId(cart), cartIdKey, cartVersionKey);
        SessionUtil.putInt(session, cartQuantityKey, cart.getTotalQuantity());
    }
    private Integer getCartTotalQuantityFromSession() {
        return SessionUtil.getIntOrNull(session, cartQuantityKey);
    }

    private void clearCartInSession() {
        SessionUtil.clearId(session, cartIdKey, cartVersionKey);
        SessionUtil.clear(session, cartQuantityKey);
    }
}