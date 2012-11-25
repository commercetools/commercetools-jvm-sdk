package sphere;

import java.util.Currency;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.CartService;
import de.commercetools.sphere.client.shop.model.Cart;
import de.commercetools.sphere.client.shop.model.Catalog;
import de.commercetools.sphere.client.shop.model.Order;
import de.commercetools.sphere.client.shop.model.PaymentState;
import de.commercetools.internal.util.Log;
import sphere.util.IdWithVersion;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.ThreadSafe;


/** Shopping cart that is automatically associated to the current HTTP session. */
@ThreadSafe
public class CurrentCart {
    private final Session session;
    private final CartService cartService;
    private Currency cartCurrency;

    public CurrentCart(CartService cartService, Currency cartCurrency) {
        this.session = Session.current();
        this.cartService = cartService;
        this.cartCurrency = cartCurrency;
    }

    /** Fetches the cart object for the current user from the backend.
     *
     *  As an optimization, the cart is only created on the backend when user puts the first product into the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to the backend. */
    public Cart fetch() {
        IdWithVersion cartId = session.getCartId();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from backend: " + cartId);
            Optional<Cart> cart = cartService.byId(cartId.id()).fetch();
            if (cart.isPresent()) {
                return cart.get();
            } else {
                Log.warn("Cart in session found in the backend: " + cartId + " Returning an empty dummy cart.");
                return Cart.createEmpty(this.cartCurrency);
            }
        } else {
            Log.trace("[cart] No cart id in session, returning an empty dummy cart.");
            // Don't create cart on the backend immediately (do it only when the customer adds a product to the cart)
            return Cart.createEmpty(this.cartCurrency);
        }
    }

    /** Returns the number of items in the cart for current user.
     *
     *  This method is purely an optimization that lets you avoid using {@link #fetch()} and then calling {@link Cart#getTotalQuantity}
     *  if the only information you need to display is the number of items in the cart.
     *  The number is stored in {@link play.mvc.Http.Session} and updated on all cart modifications. */
    public int getQuantity() {
        Integer cachedInSession = session.getCartTotalQuantity();
        Log.trace("[cart] CurrentCart.getTotalQuantity() = " + cachedInSession + " (from session).");
        return cachedInSession == null ? 0 : cachedInSession.intValue();
    }

    // --------------------------------------
    // Commands
    // --------------------------------------

    // AddLineItem --------------------------

    /**
     * Adds one item of the given products master variant from the master catalog to the cart.
     */
    public Cart addLineItem(String productId) {
        return addLineItem(productId, 1, null, null);
    }

    /**
     * Adds the given number of items of the given products master variant from the master catalog to the cart.
     */
    public Cart addLineItem(String productId, int quantity) {
        return addLineItem(productId, quantity, null, null);
    }

    /**
     * Adds one item of the given products variant from the master catalog to the cart.
     */
    public Cart addLineItem(String productId, String variantId) {
        return addLineItem(productId, 1, variantId, null);
    }

    /**
     * Adds the given number of items of the given products variant from the master catalog to the cart.
     */
    public Cart addLineItem(String productId, int quantity, String variantId) {
        return addLineItem(productId, quantity, variantId, null);
    }

    /**
     * Adds one item of the given products variant from the given catalog to the cart.
     */
    public Cart addLineItem(String productId, String variantId, Reference<Catalog> catalog) {
        return addLineItem(productId, 1, variantId, catalog);
    }

    /**
     * Adds the given number of items of the given products variant from the given catalog to the cart.
     */
    public Cart addLineItem(String productId, int quantity, String variantId, Reference<Catalog> catalog) {
        try {
            return addLineItemAsync(productId, quantity, variantId, catalog).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId) {
        return addLineItemAsync(productId, 1, null, null);
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        return addLineItemAsync(productId, quantity, null, null);
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity, String variantId) {
        return addLineItemAsync(productId, quantity, variantId, null);
    }

    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity, String variantId, Reference<Catalog> catalog) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.addLineItem(cartId.id(), cartId.version(), productId, variantId, quantity, catalog),
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
                session.clearCart(); // cart does not exist anymore
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
                session.putCart(cart);
                return cart;
            }
        });
    }

    // --------------------------------------
    // Ensure cart
    // --------------------------------------

    /** If a cart id is already in session, returns it. Otherwise creates a new cart on the backend. */
    private IdWithVersion ensureCart() {
        IdWithVersion cartId = session.getCartId();
        if (cartId == null) {
            IdWithVersion customer = session.getCustomerId();
            Log.trace("[cart] Creating a new cart on the backend and associating it with current session.");
            Cart newCart = null;
            if (customer != null)
                newCart = cartService.createCart(cartCurrency, customer.id()).execute();
            else
                newCart = cartService.createCart(cartCurrency).execute();
            session.putCart(newCart);
            cartId = new IdWithVersion(newCart.getId(), newCart.getVersion());
        }
        return cartId;
    }

}