package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnInfoDraft;

import java.util.List;

/**

    Sets return data to an order.

    {@doc.gen intro}

    {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setReturnInfo()}

 @see Order#getReturnInfo()
 */
public final class SetReturnInfo extends UpdateActionImpl<Order> {
    private final List<? extends ReturnInfoDraft> items;

    private SetReturnInfo(final List<? extends ReturnInfoDraft> items) {
        super("setReturnInfo");
        this.items = items;
    }

    public static SetReturnInfo of(final List<? extends ReturnInfoDraft> items) {
        return new SetReturnInfo(items);
    }

    public List<? extends ReturnInfoDraft> getItems() {
        return items;
    }
}
