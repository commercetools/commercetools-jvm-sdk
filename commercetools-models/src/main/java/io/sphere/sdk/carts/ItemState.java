package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;

@JsonDeserialize(as = ItemStateImpl.class)
public interface ItemState {
    static ItemState of(final Referenceable<State> state, final long quantity) {
        return new ItemStateImpl(state.toReference(), quantity);
    }

    Long getQuantity();

    Reference<State> getState();
}
