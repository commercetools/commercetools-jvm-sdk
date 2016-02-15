package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;

import java.util.Set;

/**
 * These import of states does not follow any predefined rules and should be only used if no transitions are defined.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#importLineItemState()}
 */
public final class ImportLineItemState extends UpdateActionImpl<Order> {
    private final String lineItemId;
    private final Set<ItemState> state;

    private ImportLineItemState(final String lineItemId, final Set<ItemState> state) {
        super("importLineItemState");
        this.lineItemId = lineItemId;
        this.state = state;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Set<ItemState> getState() {
        return state;
    }

    public static ImportLineItemState of(final LineItem lineItem, final Set<ItemState> itemState) {
        return of(lineItem.getId(), itemState);
    }

    public static ImportLineItemState of(final String lineItemId, final Set<ItemState> itemState) {
        return new ImportLineItemState(lineItemId, itemState);
    }
}
