package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

public class TransitionLineItemState extends UpdateAction<Order> {

    private final String lineItemId;
    private final long quantity;
    private final Reference<ItemState> fromState;
    private final Reference<ItemState> toState;

    private TransitionLineItemState(final String lineItemId, final long quantity, final Reference<ItemState> fromState,
                                    final Reference<ItemState> toState) {
        super("transitionLineItemState");
        this.lineItemId = lineItemId;
        this.quantity = quantity;
        this.fromState = fromState;
        this.toState = toState;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public Reference<ItemState> getFromState() {
        return fromState;
    }

    public Reference<ItemState> getToState() {
        return toState;
    }

    public static TransitionLineItemState of(final String lineItemId, final long quantity,
                                             final Reference<ItemState> fromState, final Reference<ItemState> toState) {
        return new TransitionLineItemState(lineItemId, quantity, fromState, toState);
    }

    public static UpdateAction<Order> of(final LineItem lineItem, final long quantity,
                                         final Reference<ItemState> fromState, final Reference<ItemState> toState) {
        return of(lineItem.getId(), quantity, fromState, toState);
    }
}

