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

    @JsonCreator
    private Cart(@JsonProperty("cartState") CartState cartState) {
        this.cartState = cartState;
    }

    public Cart(Currency currency, CartState cartState, InventoryMode inventoryMode) {
        this.totalPrice = new Money(new BigDecimal(0), currency.getCurrencyCode());
        this.cartState = cartState;
        this.inventoryMode = inventoryMode;
    }

    /** Creates an empty dummy cart (no id, empty line item list).
     *  This is useful if you want to work with a dummy instance of a cart that does not exist on the backend.
     *  To be able to modify a cart, it has to exist on the backend. */
    public static Cart createEmpty(Currency currency, InventoryMode inventoryMode) {
        // Return a dummy cart that has: currency, state, 0 line items, 0 total price
        Cart cart = new Cart(currency, CartState.Active, inventoryMode);
        cart.totalPrice = new Money(new BigDecimal(0), currency.getCurrencyCode());
        return cart;
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

        /** Orders are tracked on inventory (ordering a line item will decrement the available quantity on the inventory entry).
         * Inventory is not checked on adding line items to cart or on ordering a cart. */
        TrackOnly,

        /** When a customer orders a cart, the operation fails if some line items are not available. Line items
         * in the cart are only reserved for the duration of the ordering transaction. */
        ReserveOnOrder,

        /** Adding a line item creates a reservation in the inventory. If the request can not be satisfied, the add line
         * item command fails */
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
