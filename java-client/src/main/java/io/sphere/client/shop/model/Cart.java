package io.sphere.client.shop.model;

import java.math.BigDecimal;
import java.util.Currency;

import io.sphere.client.model.Money;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** A cart that exists in the backend. */
public class Cart extends LineItemContainer {
    @Nonnull private CartState cartState;
    @Nonnull private InventoryMode inventoryMode;

    @JsonCreator private Cart(
        @JsonProperty("cartState") CartState cartState,
        @JsonProperty("inventoryMode") InventoryMode inventoryMode) {
        this.cartState = cartState;
        this.inventoryMode = inventoryMode;
    }

    /** Creates an empty dummy cart (no id, zero price, empty line item list).
     *  This is useful if you want to work with a dummy instance of a cart that does not exist in the backend.
     *  If you want to add line items etc., the cart has to exist in the backend. */
    public static Cart createEmpty(Currency currency, InventoryMode inventoryMode) {
        return new Cart(currency, CartState.Active, inventoryMode);
    }

    // empty cart
    private Cart(Currency currency, CartState cartState, InventoryMode inventoryMode) {
        this.totalPrice = new Money(new BigDecimal(0), currency.getCurrencyCode());
        this.cartState = cartState;
        this.inventoryMode = inventoryMode;
    }

    // for tests
    protected Cart(String id, int version) {
        super(id, version);
    }

    /** Describes the state of a {@link Cart}. */
    public enum CartState {
        Active, Expiring, Expired, Canceling, Canceled, Checkout
    }

    /** Defines the cart behavior regarding inventory management. */
    public enum InventoryMode {

        /** Ordering a product variant will simply decrement available quantity on its inventory entry.
          * There are no availability checks when adding to cart or ordering.
          * Quantity can drop below zero. */
        TrackOnly,

        /** Creating an order creates a reservation for the duration of order processing.
          * If there is no sufficient quantity available, the request to create the order fails.
          * Quantity will never drop below zero. */
        ReserveOnOrder,

        /** Adding items to cart and ordering is independent of inventory.
          * No inventory checks or modifications are performed.
          * This is the default behavior. */
        None
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** The state. */
    @Nonnull public CartState getCartState() { return cartState; }

    /** Defines the cart behavior regarding inventory management. */
    @Nonnull public InventoryMode getInventoryMode() { return inventoryMode; }
}
