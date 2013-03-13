package de.commercetools.sphere.client.shop.model;

import java.math.BigDecimal;
import java.util.Currency;

import de.commercetools.sphere.client.model.Money;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** A cart that exists in the backend. */
public class Cart extends LineItemContainer {
    private CartState cartState;
    private InventoryMode inventoryMode;

    @JsonCreator private Cart(
        @JsonProperty("cartState") CartState cartState,
        @JsonProperty("inventoryMode") InventoryMode inventoryMode) {
        this.cartState = cartState;
        this.inventoryMode = inventoryMode;
    }

    /** Creates an empty dummy cart (no id, empty line item list).
     *  This is useful if you want to work with a dummy instance of a cart that does not exist on the backend.
     *  If you want to add line items etc., the cart to exist on the backend. */
    public static Cart createEmpty(Currency currency, InventoryMode inventoryMode) {
        Cart cart = new Cart(currency, CartState.Active, inventoryMode);
        cart.totalPrice = new Money(new BigDecimal(0), currency.getCurrencyCode());
        return cart;
    }

    // empty cart
    private Cart(Currency currency, CartState cartState, InventoryMode inventoryMode) {
        this.totalPrice = new Money(new BigDecimal(0), currency.getCurrencyCode());
        this.cartState = cartState;
        this.inventoryMode = inventoryMode;
    }

    /** For tests. */
    protected Cart(String id, int version) {
        super(id, version);
    }

    /** Describes the state of a Cart. */
    public enum CartState {
        Active, Expiring, Expired, Canceling, Canceled, Checkout
    }

    /** Describes the cart behavior in regards to inventory management. */
    public enum InventoryMode {

        /** Ordering a product variant will decrement available quantity on its inventory entry.
          * There are no availability checks when adding to cart or ordering.
         *  Quantity can drop below zero. */
        TrackOnly,

        /** Creating an order creates a reservation for the life time of the order processing.
          * If there is not sufficient quantity available, the create order request fails.
          * Quantity will never drop below zero. */
        ReserveOnOrder,

        /** Adding a line item creates a reservation in the inventory.
          * If there is not sufficient quantity available, the add line item request fails.
          * Quantity will never drop below zero. */
        ReserveOnAddLineItem,

        /** Adding items to cart and ordering is independent of inventory. No inventory checks or modifications. */
        None
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** The currency of this cart. */
    public Currency getCurrency() { return Currency.getInstance(totalPrice.getCurrencyCode()); }

    /** The state of this cart. */
    public CartState getCartState() { return cartState; }

    /** Defines the cart behavior in regards to inventory management. */
    public InventoryMode getInventoryMode() { return inventoryMode; }
}
