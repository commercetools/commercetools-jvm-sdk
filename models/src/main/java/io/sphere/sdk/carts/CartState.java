package io.sphere.sdk.carts;

public enum CartState {
    /**
     The cart can be updated and ordered. It is the default state.
     */
    Active,
    /**
     Anonymous cart whose content was merged into a customers cart on signin. No further operations on the cart are allowed.
     */
    Merged;

    public static CartState defaultValue() {
        return CartState.Active;
    }
}
