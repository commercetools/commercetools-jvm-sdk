package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;

import static java.util.Arrays.asList;

public enum CartState {
    /**
     The cart can be updated and ordered. It is the default state.
     */
    ACTIVE("Active"),
    /**
     Anonymous cart whose content was merged into a customers cart on signin. No further operations on the cart are allowed.
     */
    MERGED("Merged"),;

    public static CartState defaultValue() {
        return CartState.ACTIVE;
    }

    private final String value;

    CartState(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    private static CartState of(final String value) {
        return asList(values()).stream()
                .filter(element -> element.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No value for " + value + "present."));
    }
}
