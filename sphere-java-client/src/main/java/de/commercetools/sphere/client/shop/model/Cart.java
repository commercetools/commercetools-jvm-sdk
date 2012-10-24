package de.commercetools.sphere.client.shop.model;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.model.Money;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Currency;

/** A cart that exists on the backend. */
public class Cart extends LineItemContainer {
    @JsonProperty("currency")
    private Currency currency;
    private CartState cartState;

    @JsonCreator
    private Cart(@JsonProperty("currency") String currency, @JsonProperty("cartState") CartState cartState) {
        Exception ex = null;
        try {
            this.currency = Currency.getInstance(currency);
        } catch (Exception e) {
            ex = null;
        }
        if (currency == null || ex != null) {
            Log.error("Cannot parse Cart currency returned by the backend: " + currency, ex);
        }
        this.cartState = cartState;
    }

    private Cart(Currency currency, CartState cartState) {
        this.currency = currency;
        this.cartState = cartState;
    }

    /** The currency of this cart. */
    public Currency getCurrency() {
        return currency;
    }

    /** The state of this cart. */
    public CartState getCartState() {
        return cartState;
    }

    /** Creates an empty dummy cart (no id, empty line item list).
     *  This is useful if you want to work with a dummy instance of a cart that does not exist on the backend.
     *  To be able to modify a cart, it has to exist on the backend. */
    public static Cart createEmpty(Currency currency) {
        // Return a dummy cart that has: currency, state, 0 line items, 0 total price
        Cart cart = new Cart(currency, CartState.Active);
        cart.totalPrice = new Money(0, currency.getCurrencyCode());
        return cart;
    }

    /** Describes the state of a Cart. */
    public enum CartState {
        Active, Expiring, Expired, Canceling, Canceled, Checkout
    }
}
