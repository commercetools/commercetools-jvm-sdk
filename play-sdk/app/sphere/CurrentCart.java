package sphere;

import java.util.Currency;
import javax.annotation.Nullable;

import io.sphere.client.CommandRequest;
import io.sphere.client.SphereBackendException;
import io.sphere.client.SphereException;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import sphere.util.IdWithVersion;
import sphere.util.RecoverFuture;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.ThreadSafe;

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

    /** Fetches the cart object for the current user asynchronously.
     *
     *  <p><i>Note:<i> As an optimization, the cart is only created in the backend when user puts a product in the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to
     *  the backend (). */
    public Cart fetch() {
        return Util.sync(fetchAsync());
    }

    /** Fetches the cart object for the current user asynchronously.
     *
     *  <p><i>Note:<i> As an optimization, the cart is only created in the backend when user puts a product in the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to
     *  the backend (). */
    public ListenableFuture<Cart> fetchAsync() {
        final IdWithVersion cartId = session.getCartId();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from the backend: " + cartId);
            return Futures.transform(cartService.byId(cartId.getId()).fetchAsync(), new Function<Optional<Cart>, Cart>() {
                @Nullable @Override public Cart apply(@Nullable Optional<Cart> cart) {
                    if (cart.isPresent()) {
                        return cart.get();
                    } else {
                        Log.warn("[cart] Cart stored in session not found in the backend: " + cartId + " Returning an empty dummy cart.");
                        return emptyCart();
                    }
                }
            });
        } else {
            Log.trace("[cart] No cart id in session, returning an empty dummy cart.");
            // Don't create cart on the backend immediately (do it only when the customer adds a product to the cart)
            return Futures.immediateFuture(emptyCart());
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

    // UpdateCart --------------------------

    /** Updates the cart with several actions. */
    public Cart updateCart(CartUpdate update) {
        return Util.sync(updateCartAsync(update));
    }
//
    /** Updates the cart with several actions asynchronously. */
    public ListenableFuture<Cart> updateCartAsync(final CartUpdate update) {
        return Futures.transform(ensureCart(), new AsyncFunction<IdWithVersion, Cart>() {
            @Nullable @Override public ListenableFuture<Cart> apply(@Nullable IdWithVersion cartId) {
                return executeAsync(
                    cartService.updateCart(cartId.getId(), cartId.getVersion(), update),
                    String.format("[cart] Updating for cart %s.", cartId));
            }
        });
    }

    // Helpers for update

    /** Adds a product variant in the given quantity to the cart. */
    public Cart addLineItem(String productId, String variantId, int quantity) {
        return Util.sync(addLineItemAsync(productId, variantId, quantity));
    }

    /** Adds a product variant in the given quantity to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, String variantId, int quantity) {
        return updateCartAsync(new CartUpdate().addLineItem(quantity, productId, variantId));
    }

    /** Adds a product's master variant in the given quantity to the cart. */
    public Cart addLineItem(String productId, int quantity) {
        return Util.sync(addLineItemAsync(productId, quantity));
    }

    /** Adds a product's master variant in the given quantity to the cart asynchronously. */
    public ListenableFuture<Cart> addLineItemAsync(String productId, int quantity) {
        return updateCartAsync(new CartUpdate().addLineItem(quantity, productId));
    }

    /** Removes the line item from the cart. */
    public Cart removeLineItem(String lineItemId) {
        return Util.sync(removeLineItemAsync(lineItemId));
    }

    /** Removes the line item from the cart asynchronously. */
    public ListenableFuture<Cart> removeLineItemAsync(String lineItemId) {
        return updateCartAsync(new CartUpdate().removeLineItem(lineItemId));
    }

    /** Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public Cart decreaseLineItemQuantity(String lineItemId, int quantity) {
        return Util.sync(decreaseLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Decreases the quantity of the given line item asynchronously. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public ListenableFuture<Cart> decreaseLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateCartAsync(new CartUpdate().decreaseLineItemQuantity(lineItemId, quantity));
    }

    /** Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart. */
    public Cart setLineItemQuantity(String lineItemId, int quantity) {
        return Util.sync(setLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Sets the customer email in the cart. */
    public Cart setCustomerEmail(String email) {
        return Util.sync(setCustomerEmailAsync(email));
    }

    /** Sets the customer email in the cart asynchronously. */
    public ListenableFuture<Cart> setCustomerEmailAsync(String email) {
        return updateCartAsync(new CartUpdate().setCustomerEmail(email));
    }

    /** Sets the quantity of the given line item asynchronously. If quantity is 0, line item is removed from the cart. */
    public ListenableFuture<Cart> setLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateCartAsync(new CartUpdate().setLineItemQuantity(lineItemId, quantity));
    }


    /** Sets the shipping address of the cart. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. */
    public Cart setShippingAddress(Address address) {
        return Util.sync(setShippingAddressAsync(address));
    }

    /** Sets the shipping address of the cart asynchronously. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. */
    public ListenableFuture<Cart> setShippingAddressAsync(Address address) {
        return updateCartAsync(new CartUpdate().setShippingAddress(address));
    }

    /** Sets the billing address of the cart. */
    public Cart setBillingAddress(Address address) {
        return Util.sync(setBillingAddressAsync(address));
    }

    /** Sets the billing address of the cart asynchronously. */
    public ListenableFuture<Cart> setBillingAddressAsync(Address address) {
        return updateCartAsync(new CartUpdate().setBillingAddress(address));
    }

    /** Sets the country of the cart. When the country is set, the line item prices are updated. */
    public Cart setCountry(CountryCode country) {
        return Util.sync(setCountryAsync(country));
    }

    /** Sets the country of the cart asynchronously. When the country is set, the line item prices are updated. */
    public ListenableFuture<Cart> setCountryAsync(CountryCode country) {
        return updateCartAsync(new CartUpdate().setCountry(country));
    }

    /** Updates line item prices and tax rates. */
    public Cart recalculate() {
       return Util.sync(recalculateAsync());
    }

    /** Updates line item prices and tax rates asynchronously. */
    public ListenableFuture<Cart> recalculateAsync() {
        return updateCartAsync(new CartUpdate().recalculate());
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
        IdWithVersion cartId = Util.sync(ensureCart());
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
    private ListenableFuture<IdWithVersion> ensureCart() {
        IdWithVersion cartId = session.getCartId();
        if (cartId == null) {
            IdWithVersion customer = session.getCustomerId();
            Log.trace("[cart] Creating a new cart in the backend and associating it with current session.");
            ListenableFuture<Cart> newCartFuture =
                    customer != null ?
                        cartService.createCart(cartCurrency, customer.getId(), inventoryMode).executeAsync() :
                        cartService.createCart(cartCurrency, inventoryMode).executeAsync();
            return Futures.transform(newCartFuture, new Function<Cart, IdWithVersion>() {
                @Nullable @Override public IdWithVersion apply(@Nullable Cart newCart) {
                    session.putCart(newCart);
                    return new IdWithVersion(newCart.getId(), newCart.getVersion());
                }
            });
        }
        return Futures.immediateFuture(cartId);
    }
}
