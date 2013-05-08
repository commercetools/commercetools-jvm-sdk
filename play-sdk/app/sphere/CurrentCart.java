package sphere;

import java.util.Currency;
import javax.annotation.Nullable;

import io.sphere.client.CommandRequest;
import io.sphere.client.SphereBackendException;
import io.sphere.client.SphereException;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import play.libs.F.Promise;
import sphere.util.Async;
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
    private final OrderService orderService;
    private Currency cartCurrency;
    private Cart.InventoryMode inventoryMode;

    public CurrentCart(CartService cartService, OrderService orderService, Currency cartCurrency, Cart.InventoryMode inventoryMode) {
        this.session = Session.current();
        this.cartService = cartService;
        this.orderService = orderService;
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
        return Async.await(fetchAsync());
    }

    /** Fetches the cart object for the current user asynchronously.
     *
     *  <p><i>Note:<i> As an optimization, the cart is only created in the backend when user puts a product in the cart.
     *  For users who haven't put anything in their cart yet, this method returns an empty cart object without going to
     *  the backend (). */
    public Promise<Cart> fetchAsync() {
        final IdWithVersion cartId = session.getCartId();
        if (cartId != null) {
            Log.trace("[cart] Found cart id in session, fetching cart from the backend: " + cartId);
            return Async.asPlayPromise(Futures.transform(cartService.byId(cartId.getId()).fetchAsync(), new Function<Optional<Cart>, Cart>() {
                @Nullable @Override public Cart apply(@Nullable Optional<Cart> cart) {
                    if (cart.isPresent()) {
                        return cart.get();
                    } else {
                        Log.warn("[cart] Cart stored in session not found in the backend: " + cartId + " Returning an empty dummy cart.");
                        return emptyCart();
                    }
                }
            }));
        } else {
            Log.trace("[cart] No cart id in session, returning an empty dummy cart.");
            // Don't create cart on the backend immediately (do it only when the customer adds a product to the cart)
            return Promise.pure(emptyCart());
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

    /** Updates the cart, doing several modifications using one request, described by the {@code update} object. */
    public Cart update(CartUpdate update) {
        return Async.await(updateAsync(update));
    }

    /** Updates the cart asynchronously. */
    public Promise<Cart> updateAsync(final CartUpdate update) {
        return Async.asPlayPromise(Futures.transform(ensureCart(), new AsyncFunction<IdWithVersion, Cart>() {
            @Nullable @Override public ListenableFuture<Cart> apply(@Nullable IdWithVersion cartId) {
                return executeAsync(
                    cartService.updateCart(cartId.getId(), cartId.getVersion(), update),
                    String.format("[cart] Updating for cart %s.", cartId));
            }
        }));
    }

    // Helpers for update

    /** Adds a product variant in the given quantity to the cart. */
    public Cart addLineItem(String productId, String variantId, int quantity) {
        return Async.await(addLineItemAsync(productId, variantId, quantity));
    }

    /** Adds a product variant in the given quantity to the cart asynchronously. */
    public Promise<Cart> addLineItemAsync(String productId, String variantId, int quantity) {
        return updateAsync(new CartUpdate().addLineItem(quantity, productId, variantId));
    }

    /** Adds a product's master variant in the given quantity to the cart. */
    public Cart addLineItem(String productId, int quantity) {
        return Async.await(addLineItemAsync(productId, quantity));
    }

    /** Adds a product's master variant in the given quantity to the cart asynchronously. */
    public Promise<Cart> addLineItemAsync(String productId, int quantity) {
        return updateAsync(new CartUpdate().addLineItem(quantity, productId));
    }

    /** Removes the line item from the cart. */
    public Cart removeLineItem(String lineItemId) {
        return Async.await(removeLineItemAsync(lineItemId));
    }

    /** Removes the line item from the cart asynchronously. */
    public Promise<Cart> removeLineItemAsync(String lineItemId) {
        return updateAsync(new CartUpdate().removeLineItem(lineItemId));
    }

    /** Decreases the quantity of the given line item. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public Cart decreaseLineItemQuantity(String lineItemId, int quantity) {
        return Async.await(decreaseLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Decreases the quantity of the given line item asynchronously. If after the update the quantity of the line item is not greater than 0
     * the line item is removed from the cart. */
    public Promise<Cart> decreaseLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateAsync(new CartUpdate().decreaseLineItemQuantity(lineItemId, quantity));
    }

    /** Sets the quantity of the given line item. If quantity is 0, line item is removed from the cart. */
    public Cart setLineItemQuantity(String lineItemId, int quantity) {
        return Async.await(setLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Sets the customer email in the cart. */
    public Cart setCustomerEmail(String email) {
        return Async.await(setCustomerEmailAsync(email));
    }

    /** Sets the customer email in the cart asynchronously. */
    public Promise<Cart> setCustomerEmailAsync(String email) {
        return updateAsync(new CartUpdate().setCustomerEmail(email));
    }

    /** Sets the quantity of the given line item asynchronously. If quantity is 0, line item is removed from the cart. */
    public Promise<Cart> setLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateAsync(new CartUpdate().setLineItemQuantity(lineItemId, quantity));
    }

    /** Sets the shipping address of the cart. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. If null is passed as a parameter, the shipping address is unset 
     * (see {@link sphere.CurrentCart#unsetShippingAddress()}). */
    public Cart setShippingAddress(Address address) {
        return Async.await(setShippingAddressAsync(address));
    }

    /** Sets the shipping address of the cart asynchronously. Setting the shipping address also sets the tax rates of the line items
     * and calculates the taxed price. */
    public Promise<Cart> setShippingAddressAsync(Address address) {
        return updateAsync(new CartUpdate().setShippingAddress(address));
    }

    /** Unsets the shipping address. It removes the shipping address, the taxed price and the tax rates of all line items. */
    public Cart unsetShippingAddress() {
        return Async.await(setShippingAddressAsync(null));
    }

    /** Unsets the shipping address async. It removes the shipping address, the taxed price and the tax rates of all line items. */
    public Promise<Cart> unsetShippingAddressAsync(Address address) {
        return setShippingAddressAsync(null);
    }

    /** Sets the billing address of the cart. */
    public Cart setBillingAddress(Address address) {
        return Async.await(setBillingAddressAsync(address));
    }

    /** Sets the billing address of the cart asynchronously. */
    public Promise<Cart> setBillingAddressAsync(Address address) {
        return updateAsync(new CartUpdate().setBillingAddress(address));
    }

    /** Sets the country of the cart. When the country is set, the line item prices are updated. */
    public Cart setCountry(CountryCode country) {
        return Async.await(setCountryAsync(country));
    }

    /** Sets the country of the cart asynchronously. When the country is set, the line item prices are updated. */
    public Promise<Cart> setCountryAsync(CountryCode country) {
        return updateAsync(new CartUpdate().setCountry(country));
    }

    /** Updates line item prices and tax rates. */
    public Cart recalculate() {
       return Async.await(recalculateAsync());
    }

    /** Updates line item prices and tax rates asynchronously. */
    public Promise<Cart> recalculateAsync() {
        return updateAsync(new CartUpdate().recalculate());
    }

    // Checkout --------------------------------

    // to protect users from calling orderCart(createNewCheckoutId(), paymentState)
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
        // just an extra measure to prevent CurrentCart.orderCart(CurrentCart.createCheckoutSummaryId()).
        final long timeStamp;
        // just an extra measure to prevent CurrentCart.orderCart(CurrentCart.createCheckoutSummaryId()).
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
     * <p>You should always call this method before calling {#orderCart}.
     * If this method returns false, the cart was most likely changed in a different browser tab.
     * You should notify the customer and refresh the checkout page.
     *
     * @param checkoutSummaryId The identifier of the current checkout summary page. */
    public boolean isSafeToCreateOrder(String checkoutSummaryId) {
        CheckoutSummaryId checkoutId = CheckoutSummaryId.parse(checkoutSummaryId);
        if (checkoutId.appServerId.equals(thisAppServerId) && (System.currentTimeMillis() - checkoutId.timeStamp < 500)) {
            throw new SphereException(
                    "The checkoutId must be a valid string, generated when starting the checkout process. " +
                    "See the documentation of CurrentCart.orderCart().");
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
     * an id created using the {@link #createCheckoutSummaryId() createCheckoutSummaryId}
     * method when rendering the summary page, and providing the id to this method when
     * creating the order.
     *
     * <p>This method can also be used in a server-to-server callback invoked by a payment
     * gateway. Use the {@code checkoutSummaryId} as an identifier you pass to the gateway
     * and receive in the callback.
     * @param checkoutSummaryId The identifier of the checkout summary "snapshot", created using
     *                          {#createNewCheckoutId} and stored in a hidden form field of the
     *                          checkout page.
     * @param paymentState The payment state of the new order. */
    public Order createOrder(String checkoutSummaryId, PaymentState paymentState) {
        return Async.await(createOrderAsync(checkoutSummaryId, paymentState));
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
     * an id created using the {@link #createCheckoutSummaryId() createCheckoutSummaryId}
     * method when rendering the summary page, and providing the id to this method when
     * creating the order.
     *
     * <p>This method can also be used in a server-to-server callback invoked by a payment
     * gateway. Use the {@code checkoutSummaryId} as an identifier you pass to the gateway
     * and receive in the callback.
     * @param checkoutSummaryId The identifier of the checkout summary "snapshot", created using
     *                          {#createNewCheckoutId} and stored in a hidden form field of the
     *                          checkout page.
     * @param paymentState The payment state of the new order. */
    public Promise<Order> createOrderAsync(String checkoutSummaryId, PaymentState paymentState) {
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
        // If the cart was modified in the meantime, orderCart() will still fail with a ConflictException,
        // which is what we want.
        CheckoutSummaryId checkoutId = CheckoutSummaryId.parse(checkoutSummaryId);
        Log.trace(String.format("Ordering cart %s using payment state %s.", checkoutId, paymentState));
        return Async.asPlayPromise(Futures.transform(orderService.orderCart(
                checkoutId.cartId.getId(),
                checkoutId.cartId.getVersion(),
                paymentState).executeAsync(),
                new Function<Order, Order>() {
                    @Override public Order apply(@Nullable Order order) {
                    session.clearCart(); // cart does not exist anymore
                    return order;
                    }
                }));
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
                SphereException ex = Util.toSphereException(e);
                if (ex instanceof SphereBackendException && ((SphereBackendException)ex).getStatusCode() == 404) {
                    Log.warn("[cart] Cart not found (probably old cart that was deleted?). Clearing the cart: " + e.getMessage());
                    session.clearCart();
                    return emptyCart();
                }
                throw ex;
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
