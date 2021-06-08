package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;

import javax.annotation.Nullable;

/**
 * Sets the distribution channel of the given LineItem.
 * The LineItem price is updated as described in LineItem Price selection.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setLineItemDistributionChannel()}
 */
public final class SetLineItemDistributionChannel extends UpdateActionImpl<Cart> {
    @Nullable
    final private ResourceIdentifier<Channel> distributionChannel;
    final private String lineItemId;

    private SetLineItemDistributionChannel(final String lineItemId, @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        super("setLineItemDistributionChannel");
        this.lineItemId = lineItemId;
        this.distributionChannel = distributionChannel;
    }

    public static SetLineItemDistributionChannel of(final String lineItemId, @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        return new SetLineItemDistributionChannel(lineItemId, distributionChannel);
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }
}
