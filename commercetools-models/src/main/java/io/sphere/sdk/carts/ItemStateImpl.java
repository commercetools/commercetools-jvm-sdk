package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;

final class ItemStateImpl extends Base implements ItemState {
    private final Reference<State> state;
    private final Long quantity;

    @JsonCreator
    ItemStateImpl(final Reference<State> state, final Long quantity) {
        this.state = state;
        this.quantity = quantity;
    }

    public static ItemStateImpl of(final Referenceable<State> state, final long quantity) {
        return new ItemStateImpl(state.toReference(), quantity);
    }

    public Long getQuantity() {
        return quantity;
    }

    public Reference<State> getState() {
        return state;
    }
}
