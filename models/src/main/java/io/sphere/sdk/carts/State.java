package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

public class State extends Base {
    private final Reference<io.sphere.sdk.states.State> state;
    private final long quantity;

    @JsonCreator
    private State(Reference<io.sphere.sdk.states.State> state, long quantity) {
        this.state = state;
        this.quantity = quantity;
    }

    public static State of(final Reference<io.sphere.sdk.states.State> state, final long quantity) {
        return new State(state, quantity);
    }

    public long getQuantity() {
        return quantity;
    }

    public Reference<io.sphere.sdk.states.State> getState() {
        return state;
    }
}
