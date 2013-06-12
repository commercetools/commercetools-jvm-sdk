package sphere;

import java.util.Currency;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.sphere.client.CommandRequest;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.OutOfStockException;
import io.sphere.client.exceptions.PriceChangedException;
import io.sphere.client.SphereClientException;
import io.sphere.client.SphereResult;
import io.sphere.client.model.ReferenceId;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.CartService;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import play.libs.F.Promise;
import sphere.util.Async;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.ThreadSafe;

/** Shopping cart service that automatically accesses the cart associated to the current HTTP session.
 *
 * <p>A shopping cart stores most of the information that an {@link Order} does,
 * e.g. a shipping address, and it is essentially an Order in progress.
 * This means the CurrentCart is typically also used to implement checkout process. */
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

    /** Fetches the cart object for the current session asynchronously.
     *
     * <p><i>Note:<i> As an optimization, the cart is only created in the backend when user adds a first product in the cart.
     * For users who haven't put anything in their cart yet, this method returns an empty dummy cart object without going to
     * the backend. */
    public Cart fetch() {
        return Async.await(fetchAsync());
    }

    /** Fetches the cart object for the current session asynchronously.
     *
     * @see #fetch() fetch. */
    public Promise<Cart> fetchAsync() {
        final VersionedId cartId = session.getCartId();
        if (cartId != null) {
            Log.trace("[cart] Fetch: found cart id in session, fetching cart from the backend: " + cartId);
            return Async.asPlayPromise(Futures.transform(cartService.byId(cartId.getId()).fetchAsync(), new Function<Optional<Cart>, Cart>() {
                @Nullable @Override public Cart apply(@Nullable Optional<Cart> cart) {
                    if (cart.isPresent()) {
                        return cart.get();
                    } else {
                        Log.warn("[cart] Cart stored in session not found in the backend: " + cartId + ". " +
                                 "Returning an empty dummy cart and clearing cart in session.");
                        session.clearCart();
                        return emptyCart();
                    }
                }
            }));
        } else {
            Log.trace("[cart] No cartId in session, returning an empty dummy cart.");
            // Don't create cart in the backend immediately (do it only when the customer adds a product to the cart)
            return Promise.pure(emptyCart());
        }
    }

    /** Returns the number of items in the cart.
     *
     *  <p>This method exists purely as an optimization that lets you avoid calling {@link #fetch() fetch} and then
     *  {@link Cart#getTotalQuantity cart.getTotalQuantity}, if you only need to display the number of items in the cart.
     *  The quantity stored in Play's session and updated automatically on all cart modifications. */
    public int getQuantity() {
        Integer cachedInSession = session.getCartTotalQuantity();
        int quantity = cachedInSession == null ? 0 : cachedInSession;
        Log.trace("[cart] CurrentCart.getTotalQuantity() = " + quantity + " (from session).");
        return quantity;
    }

    // --------------------------------------
    // Helper methods for update()
    // --------------------------------------

    /** Adds a product variant in given quantity to the cart. */
    public Cart addLineItem(String productId, int variantId, int quantity) {
        return Async.awaitResult(addLineItemAsync(productId, variantId, quantity));
    }

    /** Adds a product variant in given quantity to the cart asynchronously. */
    public Promise<SphereResult<Cart>> addLineItemAsync(String productId, int variantId, int quantity) {
        return updateAsync(new CartUpdate().addLineItem(quantity, productId, variantId));
    }

    /** Adds product's master variant in given quantity to the cart. */
    public Cart addLineItem(String productId, int quantity) {
        return Async.awaitResult(addLineItemAsync(productId, quantity));
    }

    /** Adds product's master variant in the given quantity to the cart asynchronously. */
    public Promise<SphereResult<Cart>> addLineItemAsync(String productId, int quantity) {
        return updateAsync(new CartUpdate().addLineItem(quantity, productId));
    }

    /** Removes a line item from the cart. */
    public Cart removeLineItem(String lineItemId) {
        return Async.awaitResult(removeLineItemAsync(lineItemId));
    }

    /** Removes a line item from the cart asynchronously. */
    public Promise<SphereResult<Cart>> removeLineItemAsync(String lineItemId) {
        return updateAsync(new CartUpdate().removeLineItem(lineItemId));
    }

    /** Decreases the quantity of given line item. If after the update the quantity
     *  of the line item is not greater than 0 the line item is removed from the cart. */
    public Cart decreaseLineItemQuantity(String lineItemId, int quantity) {
        return Async.awaitResult(decreaseLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Decreases the quantity of the given line item asynchronously. If after the update the quantity
     *  of the line item is not greater than 0 the line item is removed from the cart. */
    public Promise<SphereResult<Cart>> decreaseLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateAsync(new CartUpdate().decreaseLineItemQuantity(lineItemId, quantity));
    }

    /** Sets the quantity of given line item. If the quantity is 0 the line item is removed from the cart. */
    public Cart setLineItemQuantity(String lineItemId, int quantity) {
        return Async.awaitResult(setLineItemQuantityAsync(lineItemId, quantity));
    }

    /** Sets the quantity of the given line item asynchronously. If the quantity is 0, line item is removed from the cart. */
    public Promise<SphereResult<Cart>> setLineItemQuantityAsync(String lineItemId, int quantity) {
        return updateAsync(new CartUpdate().setLineItemQuantity(lineItemId, quantity));
    }

    /** Sets the customer email for the cart.
     *  Use this method if you want to create an order for an unregistered customer. */
    public Cart setCustomerEmail(String email) {
        return Async.awaitResult(setCustomerEmailAsync(email));
    }

    /** Sets the customer email for the cart asynchronously.
     *  Use this method if you want to create an order for an unregistered customer. */
    public Promise<SphereResult<Cart>> setCustomerEmailAsync(String email) {
        return updateAsync(new CartUpdate().setCustomerEmail(email));
    }

    /** Sets the shipping address. Setting the shipping address also sets the tax rates of the line items
     *  and calculates the taxed price. If {@code null} is passed, the shipping address is cleared
     *  (see {@link #clearShippingAddress() clearShippingAddress}). */
    public Cart setShippingAddress(Address address) {
        return Async.awaitResult(setShippingAddressAsync(address));
    }

    /** Sets the shipping address asynchronously. Setting the shipping address also sets the tax rates of
     *  the line items and calculates the taxed price. If {@code null} is passed, the shipping address is cleared
     *  (see {@link #clearShippingAddressAsync() clearShippingAddressAsync}). */
    public Promise<SphereResult<Cart>> setShippingAddressAsync(Address address) {
        return updateAsync(new CartUpdate().setShippingAddress(address));
    }

    /** Clears the shipping address.
     *  Removes the shipping address, the taxed price and the tax rates of all line items. */
    public Cart clearShippingAddress() {
        return Async.awaitResult(setShippingAddressAsync(null));
    }

    /** Clears the shipping address asynchronously.
     *  Removes the shipping address, taxed price and tax rates of all line items. */
    public Promise<SphereResult<Cart>> clearShippingAddressAsync() {
        return setShippingAddressAsync(null);
    }

    /** Sets the billing address. */
    public Cart setBillingAddress(Address address) {
        return Async.awaitResult(setBillingAddressAsync(address));
    }

    /** Sets the billing address asynchronously. */
    public Promise<SphereResult<Cart>> setBillingAddressAsync(Address address) {
        return updateAsync(new CartUpdate().setBillingAddress(address));
    }

    /** Sets the country of the cart and updates line item prices. */
    public Cart setCountry(CountryCode country) {
        return Async.awaitResult(setCountryAsync(country));
    }

    /** Sets the country of the cart asynchronously and updates line item prices. */
    public Promise<SphereResult<Cart>> setCountryAsync(CountryCode country) {
        return updateAsync(new CartUpdate().setCountry(country));
    }

    /** Sets the shipping method of the cart. The cart shipping info is set and the cart total is updated. */
    public Cart setShippingMethod(ReferenceId<ShippingMethod> shippingMethod) {
        return Async.awaitResult(setShippingMethodAsync(shippingMethod));
    }

    /** Sets the shipping method of the cart asynchronously. The cart shipping info is set and the cart total is updated. */
    public Promise<SphereResult<Cart>> setShippingMethodAsync(ReferenceId<ShippingMethod> shippingMethod) {
        return updateAsync(new CartUpdate().setShippingMethod(shippingMethod));
    }

    /** Sets a custom shipping method. The cart shipping info is set and the cart total is updated.  */
    public Cart setCustomShippingMethod(String shippingMethodName, ShippingRate shippingRate, ReferenceId<TaxCategory> taxCategory) {
        return Async.awaitResult(setCustomShippingMethodAsync(shippingMethodName, shippingRate, taxCategory));
    }

    /** Sets a custom shipping method of the cart asynchronously. The cart shipping info is set and the cart total is updated. */
    public Promise<SphereResult<Cart>> setCustomShippingMethodAsync(String shippingMethodName, ShippingRate shippingRate, ReferenceId<TaxCategory> taxCategory) {
        return updateAsync(new CartUpdate().setCustomShippingMethod(shippingMethodName, shippingRate, taxCategory));
    }

    /** Updates line item prices and tax rates. This is in case that product prices, project tax settings,
     *  or project shipping settings changed since the items were added to the cart. */
    public Cart recalculate() {
       return Async.awaitResult(recalculateAsync());
    }

    /** Updates line item prices and tax rates asynchronously. This is in case that product prices, project tax settings,
     *  and project shipping settings changed since the items were added to the cart. */
    public Promise<SphereResult<Cart>> recalculateAsync() {
        return updateAsync(new CartUpdate().recalculate());
    }

    // --------------------------------------
    // Checkout
    // --------------------------------------

    // to protect users from calling orderCart(createNewCheckoutId(), paymentState)
    private static String thisAppServerId = java.util.UUID.randomUUID().toString().substring(0, 13);

    /** Creates an cart snapshot identifier for the final page of a checkout process.
     *  A final page of a checkout process is the page where the customer has the option to create an order.
     *
     * <p>See the documentation of {@link #createOrder(String, io.sphere.client.shop.model.PaymentState) createOrder}
     * for the rationale behind this method. */
    public String createCartSnapshotId() {
        VersionedId cartId = Util.syncResult(ensureCart());
        return new CartSnapshotId(cartId, System.currentTimeMillis(), thisAppServerId).toString();
    }

    /** Identifies a cart snapshot, to make sure that what is displayed is exactly what is being ordered. */
    private static class CartSnapshotId {
        final VersionedId cartId;
        // just an extra measure to prevent CurrentCart.orderCart(CurrentCart.createCheckoutSnapshotId()).
        final long timeStamp;
        // just an extra measure to prevent CurrentCart.orderCart(CurrentCart.createCheckoutSnapshotId()).
        final String appServerId;
        static final String separator = "_";
        CartSnapshotId(VersionedId cartId, long timeStamp, String appServerId) {
            this.cartId = cartId;
            this.timeStamp = timeStamp;
            this.appServerId = appServerId;
        }
        @Override public String toString() {
            return cartId.getVersion() + separator + cartId.getId() + separator + timeStamp + separator + appServerId;
        }
        static CartSnapshotId parse(String checkoutSummaryId) {
            String[] parts = checkoutSummaryId.split("_");
            if (parts.length != 4) throw new SphereClientException("Malformed checkoutId (length): " + checkoutSummaryId);
            long timeStamp;
            try {
                timeStamp = Long.parseLong(parts[2]);
            } catch (NumberFormatException ignored) {
                throw new SphereClientException("Malformed checkoutId (timestamp): " + checkoutSummaryId);
            }
            String appServerId = parts[3];
            int cartVersion;
            try {
                cartVersion = Integer.parseInt(parts[0]);
            } catch (NumberFormatException ignored) {
                throw new SphereClientException("Malformed checkoutId (version): " + checkoutSummaryId);
            }
            String cartId = parts[1];
            return new CartSnapshotId(VersionedId.create(cartId, cartVersion), timeStamp, appServerId);
        }
    }

    /** Returns true if the {@code cartSnapshotId} matches the current cart id and version,
     *  and therefore it is certain that the cart was not modified in a different browser tab during checkout.
     *
     * <p>You should always call this method before calling
     * {@link #createOrder(String, io.sphere.client.shop.model.PaymentState) createOrder}.
     * <p>If this method returns false, the cart was most likely modified in a different browser tab.
     * You should notify the customer and refresh the checkout page.
     *
     * @param cartSnapshotId The identifier of the current checkout summary page. */
    public boolean isSafeToCreateOrder(String cartSnapshotId) {
        CartSnapshotId checkoutId = CartSnapshotId.parse(cartSnapshotId);
        if (checkoutId.appServerId.equals(thisAppServerId) && (System.currentTimeMillis() - checkoutId.timeStamp < 500)) {
            throw new SphereClientException(
                    "The checkoutId must be a valid string, generated when starting the checkout process. " +
                    "See the documentation of CurrentCart.orderCart().");
        }
        VersionedId currentCartId = session.getCartId();
        // Check id just for extra safety. In practice cart id should not change
        boolean isSafeToCreateOrder = checkoutId.cartId.equals(currentCartId);
        if (!isSafeToCreateOrder) {
            Log.warn("[cart] It's not safe to order - cart was probably modified in a different browser tab.\n" +
                "checkoutId: " + checkoutId.cartId + ", current cart: " + currentCartId);
        }
        return isSafeToCreateOrder;
    }

    // --------------------------------------
    // Order
    // --------------------------------------

    /** Transforms the cart into an order.
     *
     * <p>This method should be called when the customer decides to finalize the checkout.
     *
     * <p>Before this method is called, a checkout summary page will typically display contents
     * of the current cart. There needs to be a mechanism to make sure the customer didn't add an
     * item to the cart from a different browser tab right before ordering. In other words, we
     * have to make sure that what the cart contains is exactly what is displayed.
     *
     * <p>The solution is to create a hidden HTML form input field storing an
     * {@link #createCartSnapshotId() id of a cart snapshot}, and providing the id to this
     * method when creating the order. If the cart doesn't match the provided snapshot id, this method
     * throws a {@link CartModifiedException}.
     *
     * <p><i>Note on implementing payments</i>:
     * This method can also be used in a server-to-server callback invoked by a payment
     * gateway. You should pass the a {@code cartSnapshotId} to the payment gateway,
     * receive it back in the callback and pass it to this method.
     *
     * @param cartSnapshotId A snapshot identifier of the cart from the time it was displayed to the customer
     * @param paymentState The payment state of the new order
     *
     * @throws CartModifiedException if the {@code cartSnapshotId} doesn't match the state of the current cart anymore.
     * @throws OutOfStockException if some of the products in the cart are not available anymore.
     *                             This can only happen if the cart is in the
     *                             {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     * @throws PriceChangedException if the price, tax or shipping of some line items changed since the items
     *                               were added to the cart. */
    public Order createOrder(String cartSnapshotId, PaymentState paymentState) {
        return Async.awaitResult(createOrderAsync(cartSnapshotId, paymentState));
    }

    /** Transforms the cart into an order asynchronously.
     *
     * @see #createOrder(String, io.sphere.client.shop.model.PaymentState) createOrder
     *
     * @param cartSnapshotId A snapshot identifier of the cart from the time it was displayed to the customer
     * @param paymentState The payment state of the new order
     *
     * A result which can fail with the following exceptions:
     * <ul>
     *  <li>{@link CartModifiedException} if the {@code cartSnapshotId} doesn't match the state of the current cart anymore.
     *  <li>{@link OutOfStockException} if some of the products in the cart are not available anymore.
     *      This can only happen if the cart is in the
     *      {@link io.sphere.client.shop.model.Cart.InventoryMode#ReserveOnOrder ReserveOnOrder} mode.
     *  <li>{@link PriceChangedException} if the price, tax or shipping of some line items changed since the items
     *       were added to the cart.
     * </ul> */
    public Promise<SphereResult<Order>> createOrderAsync(String cartSnapshotId, PaymentState paymentState) {
        if (session.getCartId() != null) {
            if (!isSafeToCreateOrder(cartSnapshotId)) {
                throw new CartModifiedException("The cart was likely modified in a different browser tab. " +
                    "Please call CurrentCart.isSafeToCreateOrder() before creating the order.");
            }
        }
        // session cartId can be null:
        // When a payment gateway makes a server-to-server callback request to finalize a payment,
        // there is no session associated with the request.
        // If the cart was modified in the meantime, orderCart() will still fail with a ConflictException,
        // which is what we want.
        CartSnapshotId checkoutId = CartSnapshotId.parse(cartSnapshotId);
        Log.debug(String.format("Ordering cart %s using payment state %s.", checkoutId, paymentState));
        return Async.asPlayPromise(Futures.transform(orderService.createOrder(checkoutId.cartId,paymentState).executeAsync(),
                new Function<SphereResult<Order>, SphereResult<Order>>() {
                    public SphereResult<Order> apply(@Nullable SphereResult<Order> order) {
                        session.clearCart(); // cart does not exist anymore
                        return order;
                    }
                }));
    }

    // --------------------------------------
    // Update cart
    // --------------------------------------

    /** Updates the cart, doing several modifications using one request, described by the {@code update} object. */
    public Cart update(CartUpdate update) {
        return Async.awaitResult(updateAsync(update));
    }

    /** Updates the cart asynchronously. */
    public Promise<SphereResult<Cart>> updateAsync(final CartUpdate update) {
        return Async.asPlayPromise(Futures.transform(ensureCart(), new AsyncFunction<SphereResult<VersionedId>, SphereResult<Cart>>() {
            public ListenableFuture<SphereResult<Cart>> apply(SphereResult<VersionedId> cartIdResult) {
                if (cartIdResult.isError()) {
                    // propagate the error from cart creation
                    Futures.immediateFuture(cartIdResult.<Cart>castErrorInternal());
                }
                return executeAsync(cartIdResult.getValue(), update);
            }
        }));
    }

    // helper
    private ListenableFuture<SphereResult<Cart>> executeAsync(@Nonnull final VersionedId currentCartId, @Nonnull final CartUpdate update) {
        Log.trace("[cart] Updating cart " + currentCartId);
        final CommandRequest<Cart> commandRequest = cartService.updateCart(currentCartId, update);
        return Futures.transform(commandRequest.executeAsync(), new AsyncFunction<SphereResult<Cart>, SphereResult<Cart>>() {
            @Nullable @Override public ListenableFuture<SphereResult<Cart>> apply(@Nullable SphereResult<Cart> cartResult) {
                if (cartResult.isSuccess()) {
                    session.putCart(cartResult.getValue());
                    return Futures.immediateFuture(cartResult);
                }
                final SphereResult<Cart> failedCartResult = cartResult;
                SphereBackendException e = failedCartResult.getGenericError();
                switch (e.getStatusCode()) {
                    case 404:
                        return Futures.immediateFuture(clearCartOnNotFound(e.getMessage()));
                    case 409:
                        final VersionedId cartId = session.getCartId();
                        if (cartId == null) {
                            Log.error("[cart] Cart modification failed with concurrent modification, yet there is no " +
                                      "cart in the session. This is most likely a bug you should report. " +
                                      "Clearing the cart from session as a last resort.");
                            session.clearCart();
                            return Futures.immediateFuture(failedCartResult);
                        }
                        Log.warn("[cart] ConcurrentModification error when modifying the cart " + cartId + ". " +
                                 "Ignoring the modification and repairing session state. " + e.getMessage());
                        return Futures.transform(cartService.byId(cartId.getId()).fetchAsync(), new Function<Optional<Cart>, SphereResult<Cart>>() {
                            @Nullable @Override public SphereResult<Cart> apply(Optional<Cart> existingCart) {
                                if (!existingCart.isPresent()) {
                                    return clearCartOnNotFound(cartId.toString());
                                } else {
                                    return SphereResult.success(session.putCart(existingCart.get()));
                                }
                            }
                        });
                    default:
                        Futures.immediateFuture(failedCartResult);
                }
                return Futures.immediateFuture(failedCartResult);
            }
        });
    }

    private SphereResult<Cart> clearCartOnNotFound(String msg) {
        Log.warn("[cart] Cart not found (probably old cart that was deleted?)." +
                 "Clearing the cart from session. " + msg);
        session.clearCart();
        return SphereResult.success(emptyCart());
    }

    // --------------------------------------
    // Ensure cart
    // --------------------------------------

    /** If there is a cart id in the session, returns it. Otherwise creates a new cart in the backend. */
    private ListenableFuture<SphereResult<VersionedId>> ensureCart() {
        VersionedId cartId = session.getCartId();
        if (cartId != null) {
            return Futures.immediateFuture(SphereResult.success(cartId));
        } else {
            VersionedId customerId = session.getCustomerId();
            return Futures.transform(getExistingCartOrCreateNew(customerId), new Function<SphereResult<Cart>, SphereResult<VersionedId>>() {
                @Nullable @Override public SphereResult<VersionedId> apply(@Nullable SphereResult<Cart> existingOrNewCart) {
                    if (existingOrNewCart.isSuccess()) {
                        session.putCart(existingOrNewCart.getValue());
                    }
                    return existingOrNewCart.transform(new Function<Cart, VersionedId>() {
                        public VersionedId apply(@Nullable Cart cart) {
                            return cart.getIdAndVersion();
                        }
                    });
                }
            });
        }
    }

    /** Gets customer's cart, or creates a new one if the customer has no cart or the customer is null. */
    private ListenableFuture<SphereResult<Cart>> getExistingCartOrCreateNew(final VersionedId customerId) {
        ListenableFuture<Optional<Cart>> getFuture =
                customerId == null ?
                        Futures.immediateFuture(Optional.<Cart>absent()) :
                        cartService.forCustomer(customerId.getId()).fetchAsync();
        return Futures.transform(getFuture, new AsyncFunction<Optional<Cart>, SphereResult<Cart>>() {
            @Override
            public ListenableFuture<SphereResult<Cart>> apply(Optional<Cart> existingCart) throws Exception {
                if (existingCart.isPresent()) {
                    return Futures.immediateFuture(SphereResult.success(existingCart.get()));
                } else {
                    return customerId != null ?
                            cartService.createCart(cartCurrency, customerId.getId(), inventoryMode).executeAsync() :
                            cartService.createCart(cartCurrency, inventoryMode).executeAsync();
                }
            }
        });
    }
}
