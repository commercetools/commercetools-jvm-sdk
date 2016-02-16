package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;

import java.util.Set;

/**
 * These import of states does not follow any predefined rules and should be only used if no transitions are defined.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#importCustomLineItemState()}
 */
public final class ImportCustomLineItemState extends UpdateActionImpl<Order> {
    private final String customLineItemId;
    private final Set<ItemState> state;

    private ImportCustomLineItemState(final String customLineItemId, final Set<ItemState> state) {
        super("importCustomLineItemState");
        this.customLineItemId = customLineItemId;
        this.state = state;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    public Set<ItemState> getState() {
        return state;
    }

    public static ImportCustomLineItemState of(final CustomLineItem CustomLineItem, final Set<ItemState> itemState) {
        return of(CustomLineItem.getId(), itemState);
    }

    public static ImportCustomLineItemState of(final String CustomLineItemId, final Set<ItemState> itemState) {
        return new ImportCustomLineItemState(CustomLineItemId, itemState);
    }
}
