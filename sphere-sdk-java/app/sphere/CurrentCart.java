package sphere;

import java.util.Currency;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.CartService;
import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.internal.util.Log;
import sphere.util.IdWithVersion;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import net.jcip.annotations.ThreadSafe;

/** Shopping cart that is automatically associated to the current HTTP session.
 *
 * A shopping cart stores most of the information that an {@link Order} does,
 * e.g. a shipping address, and it is, essentially, an Order in progress.
 * This means that the CurrentCart is also used for the implementation of checkout process. */
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
        int quantity = cachedInSession == null ? 0 : cachedInSession;
        Log.trace("[cart] CurrentCart.getTotalQuantity() = " + quantity + " (from session).");
        return quantity;
    }

    // --------------------------------------
    // Commands
    // --------------------------------------

    // AddLineItem --------------------------

    /** Adds the master variant of a product to the cart. */
    public Cart addLineItem(String productId, int quantity) {
        return addLineItem(productId, 1, null, quantity);
    }

    /** Adds a specific product variant to the cart. */
    public Cart addLineItem(String productId, int variantId, int quantity) {
        return addLineItem(productId, variantId, null, quantity);
    }

    /** Adds the master variant of a product from a specific catalog to the cart. */
    public Cart addLineItem(String productId, Reference<Catalog> catalog, int quantity) {
        return addLineItem(productId, 1, catalog, quantity);
    }

    /** Adds a specific product variant from a specific catalog to the cart. */
    public Cart addLineItem(String productId, int variantId, Reference<Catalog> catalog, int quantity) {
        try {
            return addLineItemAsync(productId, variantId, catalog, quantity).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    /** Adds the master variant of a product to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        return addLineItemAsync(productId, 1, null, quantity);
    }

    /** Adds a specific product variant to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, int variantId, int quantity) {
        return addLineItemAsync(productId, variantId, null, quantity);
    }

    /** Adds the master variant of a product from a specific catalog to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, Reference<Catalog> catalog, int quantity) {
        return addLineItemAsync(productId, 1, catalog, quantity);
    }

    /** Adds a specific product variant from a specific catalog to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, int variantId, Reference<Catalog> catalog, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.addLineItem(cartId.id(), cartId.version(), productId, variantId, quantity, catalog),
                String.format("[cart] Adding product %s to cart %s.", productId, cartId));
    }

    // RemoveLineItem -----------------------

    /** Removes a line item from the cart. */
    public Cart removeLineItem(String lineItemId) {
        try {
            return removeLineItemAsync(lineItemId).get();
        } catch (Exception e) {
            throw new SphereException(e);
        }
    }

    /** Removes a line item from the cart. */
    public ListenableFuture<Cart> removeLineItemAsync(String lineItemId) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.removeLineItem(cartId.id(), cartId.version(), lineItemId),
                String.format("[cart] Removing line item %s from cart %s.", lineItemId, cartId));
    }

    // UpdateLineItemQuantity ---------------

    /** Sets the quantity of a line item to a specific value. */
    public Cart updateLineItemQuantity(String lineItemId, int quantity) {
        try {
            return updateLineItemQuantityAsync(lineItemId, quantity).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    /** Sets the quantity of a line item to a specific value asynchronously. */
    public ListenableFuture<Cart> updateLineItemQuantityAsync(String lineItemId, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.updateLineItemQuantity(cartId.id(), cartId.version(), lineItemId, quantity),
                String.format("[cart] Updating quantity of line item %s to %s in cart %s.", lineItemId, quantity, cartId));
    }

    // SetShippingAddress -------------------

    /** Sets the shipping address to a specific value. */
    public Cart setShippingAddress(Address address) {
        try {
            return setShippingAddressAsync(address).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    /** Sets the shipping address to a specific value asynchronously. */
    public ListenableFuture<Cart> setShippingAddressAsync(Address address) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setShippingAddress(cartId.id(), cartId.version(), address),
                String.format("[cart] Setting address for cart %s.", cartId));  // don't log personal data
    }

    // Checkout --------------------------------

    // to protect users from calling createOrder(createNewCheckoutId(), paymentState)
    private static String thisAppServerId = java.util.UUID.randomUUID().toString().substring(0, 13);

    /** Creates an identifier of a checkout process for a single browser tab.
     *
     * Store this identifier in a hidden form field, pass it between the checkout steps
     * if any, and finally provide it to {#createOrder}. */
    public String createNewCheckoutId() {
        IdWithVersion cartId = ensureCart();
        return cartId.version() + "_" + cartId.id() + "_" + System.currentTimeMillis() + "_" + thisAppServerId;
    }

    /** Returns true if it is sure that the cart was not modified in a separate browser tab during checkout.
     *
     * You should call this method before calling {#createOrder}.
     * If this method returns false, you can for example notify the customer and refresh the checkout page.
     *
     * @param checkoutId The id of the current checkout, associated with the current browser tab using
     * a hidden form field. */
    public boolean isSafeToCreateOrder(String checkoutId) {
        String[] parts = checkoutId.split("_");
        if (parts.length != 4) throw new IllegalArgumentException("Malformed checkoutId (length): " + checkoutId);
        long timeStamp;
        try {
            timeStamp = Long.parseLong(parts[2]);
        } catch (NumberFormatException ignored) {
            throw new IllegalArgumentException("Malformed checkoutId (timestamp): " + checkoutId);
        }
        String appId = parts[3];
        if (appId.equals(thisAppServerId) && (System.currentTimeMillis() - timeStamp < 500)) {
            throw new IllegalStateException(
                    "The checkoutId must be created when starting a checkout and sent back by the browser " +
                    "when creating an order. See out the documentation of CurrentCart.createOrder().");
        }

        int version;
        try {
            version = Integer.parseInt(parts[0]);
        } catch (NumberFormatException ignored) {
            throw new IllegalArgumentException("Malformed checkoutId (version): " + checkoutId);
        }
        String id = parts[1];
        IdWithVersion currentCartId = session.getCartId();
        // the id check is just for extra safety, in practice cart id should not change
        boolean isSafeToCreateOrder = (version == currentCartId.version() && id.equals(currentCartId.id()));
        if (!isSafeToCreateOrder) {
            Log.warn("It's not safe to order - cart was probably modified in a different browser tab / window.\n" +
                "checkoutId: " + new IdWithVersion(id, version) + ", current cart: " + currentCartId);
        }
        return isSafeToCreateOrder;
    }

    // Order --------------------------------

    /** Transforms the cart into an order.
     *
     * This method should be called when the customer decides to finalize the checkout.
     * Because a checkout summary page will typically display contents of the current
     * cart (with shipping address and other information that is also part of the cart),
     * there needs to be a mechanism to make sure the customer didn't add an item to the
     * cart on a different browser tab.
     * The correct way to ensure that the customer is ordering exactly what is displayed
     * on the checkout page is to create a hidden HTML form input field storing {#createNewCheckoutId}
     * when starting the checkout, and providing this id back when finalizing the checkout.
     * @param checkoutId The checkout id parsed from a hidden form field.
     * @param paymentState The payment state of the new order. */
    public Order createOrder(String checkoutId, PaymentState paymentState) {
        try {
            return createOrderAsync(checkoutId, paymentState).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    /** Transforms the cart into an order asynchronously.
     *
     * This method should be called when the customer decides to finalize the checkout.
     * Because a checkout summary page will typically display contents of the current
     * cart (with shipping address and other information that is also part of the cart),
     * there needs to be a mechanism to make sure the customer didn't add an item to the
     * cart on a different browser tab.
     * The correct way to ensure that the customer is ordering exactly what is displayed
     * on the checkout page is to create a hidden HTML form input field storing {#createNewCheckoutId}
     * when starting the checkout, and providing this id back when finalizing the checkout.
     * @param checkoutId The checkout id parsed from a hidden form field.
     * @param paymentState The payment state of the new order. */
    public ListenableFuture<Order> createOrderAsync(String checkoutId, PaymentState paymentState) {
        IdWithVersion cartId = session.getCartId();
        if (cartId == null) {
            throw new IllegalStateException("Can't create on order before adding something to a cart.");
        }
        if (!isSafeToCreateOrder(checkoutId)) {
            throw new IllegalStateException("The cart was most likely modified in a different browser tab. " +
                    "Please call CurrentCart.isSafeToCreateOrder() before creating the order.");
        }
        Log.trace(String.format("Ordering cart %s using payment state %s.", cartId, paymentState));
        return Futures.transform(cartService.createOrder(cartId.id(), cartId.version(), paymentState).executeAsync(), new Function<Order, Order>() {
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

    /** If there is a cart id in the session, returns it. Otherwise creates a new cart in the backend. */
    private IdWithVersion ensureCart() {
        IdWithVersion cartId = session.getCartId();
        if (cartId == null) {
            IdWithVersion customer = session.getCustomerId();
            Log.trace("[cart] Creating a new cart on the backend and associating it with current session.");
            Cart newCart = customer != null ?
                cartService.createCart(cartCurrency, customer.id()).execute() :
                cartService.createCart(cartCurrency).execute();
            session.putCart(newCart);
            cartId = new IdWithVersion(newCart.getId(), newCart.getVersion());
        }
        return cartId;
    }
}