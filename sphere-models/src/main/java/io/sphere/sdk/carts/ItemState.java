package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;

public class ItemState extends Base {
    private final Reference<State> state;
    private final long quantity;

    @JsonCreator
    private ItemState(final Reference<State> state, final long quantity) {
        this.state = state;
        this.quantity = quantity;
    }

    public static ItemState of(final Referenceable<State> state, final long quantity) {
        return new ItemState(state.toReference(), quantity);
    }

    public long getQuantity() {
        return quantity;
    }

    public Reference<State> getState() {
        return state;
    }
}
