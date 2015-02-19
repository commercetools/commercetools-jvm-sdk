package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

public class ItemState extends Base {
    private final Reference<State> state;
    private final long quantity;

    @JsonCreator
    private ItemState(Reference<State> state, long quantity) {
        this.state = state;
        this.quantity = quantity;
    }

    public static ItemState of(final Reference<State> state, final long quantity) {
        return new ItemState(state, quantity);
    }

    public long getQuantity() {
        return quantity;
    }
}
