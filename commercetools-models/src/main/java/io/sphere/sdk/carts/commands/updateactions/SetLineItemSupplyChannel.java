package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;

/**
 * Sets the supply channel of the given LineItem.
 * The LineItem price is updated as described in LineItem Price selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest}
 */
public final class SetLineItemSupplyChannel extends UpdateActionImpl<Cart> {

    final private String lineItemId;
    @Nullable
    final private Reference<Channel> supplyChannel;

    private SetLineItemSupplyChannel(final String lineItemId, @Nullable final Reference<Channel> supplyChannel) {
        super("setLineItemSupplyChannel");
        this.lineItemId = lineItemId;
        this.supplyChannel = supplyChannel;
    }

    public static SetLineItemSupplyChannel of(final String lineItemId, @Nullable final Reference<Channel> supplyChannel) {
        return new SetLineItemSupplyChannel(lineItemId, supplyChannel);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
