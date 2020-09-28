package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

/**
 * Set a Distribution Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getDistributionChannels()
 */
public class SetDistributionChannels extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<Channel> distributionChannels;

    private SetDistributionChannels(final Reference<Channel> distributionChannels) {
        super("setDistributionChannel");
        this.distributionChannels = distributionChannels;
    }

    public ResourceIdentifier<Channel> getDistributionChannel() {
        return distributionChannels;
    }

    public static SetDistributionChannels of(final Referenceable<Channel> distributionChannels) {
        return new SetDistributionChannels(distributionChannels.toReference());
    }
}
