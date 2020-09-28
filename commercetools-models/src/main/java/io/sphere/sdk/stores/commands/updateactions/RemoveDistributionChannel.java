package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

/**
 * Remove a Distribution Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getDistributionChannels()
 */
public final class RemoveDistributionChannel extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<Channel> distributionChannel;

    private RemoveDistributionChannel(final Reference<Channel> distributionChannel) {
        super("removeDistributionChannel");
        this.distributionChannel = distributionChannel;
    }

    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannel;
    }

    public static RemoveDistributionChannel of(final Referenceable<Channel> distributionChannel) {
        return new RemoveDistributionChannel(distributionChannel.toReference());
    }
}
