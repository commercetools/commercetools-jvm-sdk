package sphere;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.client.CommandRequest;
import io.sphere.client.SphereBackendException;
import io.sphere.client.SphereException;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import net.jcip.annotations.ThreadSafe;
import sphere.util.IdWithVersion;
import sphere.util.RecoverFuture;

import javax.annotation.Nullable;
import java.util.Currency;

/** Shopping cart that is automatically associated to the current HTTP session.
 *
 * <p>A shopping cart stores most of the information that an {@link Order} does,
 * e.g. a shipping address, and it is essentially an Order in progress.
 * This means the CurrentCart will be typically also be used to implement checkout process. */
@ThreadSafe
public class CurrentCart {
    private final Session session;
    private final CartService cartService;
    private Currency cartCurrency;
    private Cart.InventoryMode inventoryMode;

    public CurrentCart(CartService cartService, Currency cartCurrency, Cart.InventoryMode inventoryMode) {
        this.session = Session.current();
        this.cartService = cartService;
        this.cartCurrency = cartCurrency;
        this.inventoryMode = inventoryMode;
    }

    private Cart emptyCart() {
        return Cart.createEmpty(this.cartCurrency, this.inventoryMode);
    }

    /** Fetches the cart object for the current user from the backend.
     *
     *  <p><i>Note:<i> As an optimization, the cart is only created in the backend when user puts a product in the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to
     *  the backend (). */
    public Cart fetch() {
        IdWithVersion cartId = session.getCartId();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from the backend: " + cartId);
            Optional<Cart> cart = cartService.byId(cartId.getId()).fetch();
            if (cart.isPresent()) {
                return cart.get();
            } else {
                Log.warn("[cart] Cart stored in session not found in the backend: " + cartId + " Returning an empty dummy cart.");
                return emptyCart();
            }
        } else {
            Log.trace("[cart] No cart id in session, returning an empty dummy cart.");
            // Don't create cart on the backend immediately (do it only when the customer adds a product to the cart)
            return emptyCart();
        }
    }

    /** Returns the number of items in the cart for current user.
     *
     *  This method is purely an optimization that lets you avoid using {@link #fetch} and then calling
     *  {@link Cart#getTotalQuantity} if you only need to display is the number of items in the cart.
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
        return addLineItem(productId, "1", null, quantity);
    }

    /** Adds a specific product variant to the cart. */
    public Cart addLineItem(String productId, String variantId, int quantity) {
        return addLineItem(productId, variantId, null, quantity);
    }

    /** Adds the master variant of a product from a specific catalog to the cart. */
    public Cart addLineItem(String productId, Reference<Catalog> catalog, int quantity) {
        return addLineItem(productId, "1", catalog, quantity);
    }

    /** Adds a specific product variant from a specific catalog to the cart. */
    public Cart addLineItem(String productId, String variantId, Reference<Catalog> catalog, int quantity) {
        return Util.sync(addLineItemAsync(productId, variantId, catalog, quantity));
    }

    /** Adds the master variant of a product to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        return addLineItemAsync(productId, "1", null, quantity);
    }

    /** Adds a specific product variant to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, String variantId, int quantity) {
        return addLineItemAsync(productId, variantId, null, quantity);
    }

    /** Adds the master variant of a product from a specific catalog to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, Reference<Catalog> catalog, int quantity) {
        return addLineItemAsync(productId, "1", catalog, quantity);
    }

    /** Adds a specific product variant from a specific catalog to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, String variantId, Reference<Catalog> catalog, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.addLineItem(cartId.getId(), cartId.getVersion(), productId, variantId, quantity, catalog),
                String.format("[cart] Adding product %s to cart %s.", productId, cartId));
    }

    // RemoveLineItem -----------------------

    /** Removes the line item from given cart. */
    public Cart removeLineItem(String lineItemId) {
        return Util.sync(removeLineItemAsync(lineItemId));
    }

    /** Removes the line item from given cart. */
    public ListenableFuture<Cart> removeLineItemAsync(String lineItemId) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.removeLineItem(cartId.getId(), cartId.getVersion(), lineItemId),
                String.format("[cart] Removing line item %s from cart %s.", lineItemId, cartId));
    }

    /** Decreases the line item quantity from given cart and returns the updated Cart.
     *  If quantity of the line item is 0 after the update, the line item is removed from the cart. */
    public Cart decreaseLineItemQuantity(String lineItemId, int quantity) {
        return Util.sync(decreaseLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Decreases the line item quantity from given cart and returns the updated Cart.
     *  If quantity of the line item is 0 after the update, the line item is removed from the cart. */
    public ListenableFuture<Cart> decreaseLineItemQuantityAsync(String lineItemId, int quantity) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.decreaseLineItemQuantity(cartId.getId(), cartId.getVersion(), lineItemId, quantity),
                String.format("[cart] Decreasing %s items of line item %s from cart %s.", quantity, lineItemId, cartId));
    }

    // SetShippingAddress -------------------

    /** Sets the shipping address to a specific value. */
    public Cart setShippingAddress(Address address) {
        return Util.sync(setShippingAddressAsync(address));
    }

    /** Sets the shipping address to a specific value asynchronously. */
    public ListenableFuture<Cart> setShippingAddressAsync(Address address) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setShippingAddress(cartId.getId(), cartId.getVersion(), address),
                String.format("[cart] Setting address for cart %s.", cartId));  // don't log personal data
    }

    // SetBillingAddress -------------------

    /** Sets the billing address to a specific value. */
    public Cart setBillingAddress(Address address) {
        return Util.sync(setBillingAddressAsync(address));
    }

    /** Sets the billing address to a specific value asynchronously. */
    public ListenableFuture<Cart> setBillingAddressAsync(Address address) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setBillingAddress(cartId.getId(), cartId.getVersion(), address),
                String.format("[cart] Setting address for cart %s.", cartId));  // don't log personal data
    }

    // SetCountry -------------------

    /** Sets the country of the cart. */
    public Cart setCountry(CountryCode country) {
        return Util.sync(setCountryAsync(country));
    }

    /** Sets the country of the cart asynchronously. */
    public ListenableFuture<Cart> setCountryAsync(CountryCode country) {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.setCountry(cartId.getId(), cartId.getVersion(), country),
                String.format("[cart] Setting country for cart %s.", cartId));  // don't log personal data
    }

    // Recalculate Cart --------------

    /** Updates all line item prices and recalculates the total. */
    public Cart recalculatePrices() {
        return Util.sync(recalculatePricesAsync());
    }

    /** Updates all line item prices and recalculates the total asynchronously. */
    public ListenableFuture<Cart> recalculatePricesAsync() {
        IdWithVersion cartId = ensureCart();
        return executeAsync(
                cartService.recalculatePrices(cartId.getId(), cartId.getVersion()),
                String.format("[cart] Recalculating prices for cart %s.", cartId));  // don't log personal data
    }

    
    // Checkout --------------------------------

    // to protect users from calling createOrder(createNewCheckoutId(), paymentState)
    private static String thisAppServerId = java.util.UUID.randomUUID().toString().substring(0, 13);

    /** Creates an identifier for the final page of a checkout process.
     *
     * A final page of a checkout process is the page where the customer has the option to create an order.
     *
     * <p>The purpose of this method is to prevent changes to the cart in other browser tabs right before the customer
     * clicks "Order", which could result in ordering something else than what the customer sees. To prevent this,
     * store the identifier in a hidden form field in the checkout summary page and provide it when calling
     * {@link #createOrder}. */
    public String createCheckoutSummaryId() {
        IdWithVersion cartId = ensureCart();
        return new CheckoutSummaryId(cartId, System.currentTimeMillis(), thisAppServerId).toString();
    }

    /** Identifies a cart snapshot, to make sure that what is displayed is exactly what is being ordered. */
    private static class CheckoutSummaryId {
        final IdWithVersion cartId;
        // just an extra measure to prevent CurrentCart.createOrder(CurrentCart.createCheckoutSummaryId()).
        final long timeStamp;
        // just an extra measure to prevent CurrentCart.createOrder(CurrentCart.createCheckoutSummaryId()).
        final String appServerId;
        static final String separator = "_";
        CheckoutSummaryId(IdWithVersion cartId, long timeStamp, String appServerId) {
            this.cartId = cartId;
            this.timeStamp = timeStamp;
            this.appServerId = appServerId;
        }
        @Override public String toString() {
            return cartId.getVersion() + separator + cartId.getId() + separator + timeStamp + separator + appServerId;
        }
        static CheckoutSummaryId parse(String checkoutSummaryId) {
            String[] parts = checkoutSummaryId.split("_");
            if (parts.length != 4) throw new SphereException("Malformed checkoutId (length): " + checkoutSummaryId);
            long timeStamp;
            try {
                timeStamp = Long.parseLong(parts[2]);
            } catch (NumberFormatException ignored) {
                throw new SphereException("Malformed checkoutId (timestamp): " + checkoutSummaryId);
            }
            String appServerId = parts[3];
            int cartVersion;
            try {
                cartVersion = Integer.parseInt(parts[0]);
            } catch (NumberFormatException ignored) {
                throw new SphereException("Malformed checkoutId (version): " + checkoutSummaryId);
            }
            String cartId = parts[1];
            return new CheckoutSummaryId(new IdWithVersion(cartId, cartVersion), timeStamp, appServerId);
        }
    }

    /** Returns true if it is certain that the cart was not modified in a different browser tab during checkout.
     *
     * <p>You should always call this method before calling {#createOrder}.
     * If this method returns false, the cart was most likely changed in a different browser tab.
     * You should notify the customer and refresh the checkout page.
     *
     * @param checkoutSummaryId The identifier of the current checkout summary page. */
    public boolean isSafeToCreateOrder(String checkoutSummaryId) {
        CheckoutSummaryId checkoutId = CheckoutSummaryId.parse(checkoutSummaryId);
        if (checkoutId.appServerId.equals(thisAppServerId) && (System.currentTimeMillis() - checkoutId.timeStamp < 500)) {
            throw new SphereException(
                    "The checkoutId must be a valid string, generated when starting the checkout process. " +
                    "See the documentation of CurrentCart.createOrder().");
        }
        IdWithVersion currentCartId = session.getCartId();
        // Check id just for extra safety. In practice cart id should not change
        boolean isSafeToCreateOrder = checkoutId.cartId.equals(currentCartId);
        if (!isSafeToCreateOrder) {
            Log.warn("[cart] It's not safe to order - cart was probably modified in a different browser tab.\n" +
                "checkoutId: " + checkoutId.cartId + ", current cart: " + currentCartId);
        }
        return isSafeToCreateOrder;
    }

    // Order --------------------------------

    /** Transforms the cart into an order.
     *
     * <p>This method should be called when the customer decides to finalize the checkout.
     * Because a checkout summary page will typically display contents of the current
     * cart, there needs to be a mechanism to make sure the customer didn't add an item
     * to the cart in a different browser tab.
     *
     * <p>The correct way to ensure that the customer is ordering exactly what is displayed
     * on the checkout summary page is to create a hidden HTML form input field storing
     * an id created using the {#createNewCheckoutId} method when rendering the summary page,
     * and providing the id to this method when creating the order.
     * @param checkoutSummaryId The identifier of the checkout summary "snapshot", created using
     *                          {#createNewCheckoutId} and stored in a hidden form field of the
     *                          checkout page.
     * @param paymentState The payment state of the new order. */
    public Order createOrder(String checkoutSummaryId, PaymentState paymentState) {
        return Util.sync(createOrderAsync(checkoutSummaryId, paymentState));
    }

    /** Transforms the cart into an order asynchronously.
     *
     * <p>This method should be called when the customer decides to finalize the checkout.
     * Because a checkout summary page will typically display contents of the current
     * cart, there needs to be a mechanism to make sure the customer didn't add an item
     * to the cart in a different browser tab.
     *
     * <p>The correct way to ensure that the customer is ordering exactly what is displayed
     * on the checkout summary page is to create a hidden HTML form input field storing
     * an id created using the {#createNewCheckoutId} method when rendering the summary page,
     * and providing the id to this method when creating the order.
     * @param checkoutSummaryId The identifier of the checkout summary "snapshot", created using
     *                          {#createNewCheckoutId} and stored in a hidden form field of the
     *                          checkout page.
     * @param paymentState The payment state of the new order. */
    public ListenableFuture<Order> createOrderAsync(String checkoutSummaryId, PaymentState paymentState) {
        IdWithVersion cartId = session.getCartId();
        if (cartId != null) {
            // Provide a nicer error message if we have a session
            if (!isSafeToCreateOrder(checkoutSummaryId)) {
                throw new SphereException("The cart was likely modified in a different browser tab. " +
                    "Please call CurrentCart.isSafeToCreateOrder() before creating the order.");
            }
        }
        // cartId can be null:
        // When a payment gateway makes a server-to-server callback request to finalize a payment,
        // there is no session associated with the request.
        // If the cart was modified in the meantime, createOrder() will still fail with a ConflictException,
        // which is what we want.
        CheckoutSummaryId checkoutId = CheckoutSummaryId.parse(checkoutSummaryId);
        Log.trace(String.format("Ordering cart %s using payment state %s.", cartId, paymentState));
        return Futures.transform(cartService.createOrder(
                checkoutId.cartId.getId(),
                checkoutId.cartId.getVersion(),
                paymentState).executeAsync(),
                new Function<Order, Order>() {
                    @Override public Order apply(@Nullable Order order) {
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
        ListenableFuture<Cart> fetchFuture = Futures.transform(commandRequest.executeAsync(), new Function<Cart, Cart>() {
            @Override public Cart apply(@Nullable Cart cart) {
                session.putCart(cart);
                return cart;
            }
        });
        return RecoverFuture.recover(fetchFuture, new Function<Throwable, Cart>() {
            @Nullable @Override public Cart apply(@Nullable Throwable e) {
                if (e.getCause() instanceof SphereException) e = e.getCause();
                if (e instanceof SphereBackendException && ((SphereBackendException) e).getStatusCode() == 404) {
                    Log.warn("[cart] Cart not found (probably old cart that was deleted?). Clearing the cart: " + e.getMessage());
                    session.clearCart();
                    return emptyCart();
                }
                if (e instanceof SphereException) throw (SphereException)e;
                throw new SphereException(e);
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
            Log.trace("[cart] Creating a new cart in the backend and associating it with current session.");
            Cart newCart =
                    customer != null ?
                        cartService.createCart(cartCurrency, customer.getId(), inventoryMode).execute() :
                        cartService.createCart(cartCurrency, inventoryMode).execute();
            session.putCart(newCart);
            cartId = new IdWithVersion(newCart.getId(), newCart.getVersion());
        }
        return cartId;
    }
}
