package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonProperty;

/** A cart that exists on the backend. */
public class Cart extends LineItemContainer {
    @JsonProperty("currency")
    private String currency;
    private CartState cartState;

    // for JSON deserializer
    private Cart() {}

    /** The currency of this cart. */
    public String getCurrencyCode() {
        return currency;
    }

    /** The state of this cart. */
    public CartState getCartState() {
        return cartState;
    }

    /** Returns an empty dummy cart.
     *  This is useful if you want to work with a dummy instance of a cart that does not exist on the backend.
     *  To modify a cart, it has to exist on the backend. */
    public static Cart empty() {
        return new Cart();
    }

    /** Describes the state of a Cart. */
    public enum CartState {
        Active, Expiring, Expired, Canceling, Canceled, Checkout
    }
}
