package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalLineItemTotalPrice;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;

/**
 * Sets the distribution channel of the given LineItem.
 * The LineItem price is updated as described in LineItem Price selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest}
 */
public final class SetLineItemDistributionChannel extends UpdateActionImpl<Cart> {

    final private String lineItemId;
    @Nullable
    final private Reference<Channel> distributionChannel;

    private SetLineItemDistributionChannel(final String lineItemId, @Nullable final Reference<Channel> distributionChannel) {
        super("setLineItemDistributionChannel");
        this.lineItemId = lineItemId;
        this.distributionChannel = distributionChannel;
    }

    public static SetLineItemDistributionChannel of(final String lineItemId, @Nullable final Reference<Channel> distributionChannel) {
        return new SetLineItemDistributionChannel(lineItemId, distributionChannel);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }
}
