package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Set a Distribution Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getDistributionChannels()
 */
public final class SetDistributionChannels extends UpdateActionImpl<Store> {
    private final List<ResourceIdentifier<Channel>> distributionChannels;

    private SetDistributionChannels(final List<ResourceIdentifier<Channel>> distributionChannels) {
        super("setDistributionChannels");
        this.distributionChannels = distributionChannels;
    }

    public List<ResourceIdentifier<Channel>> getDistributionChannels() {
        return distributionChannels;
    }

    public static SetDistributionChannels of(final List<Referenceable<Channel>> distributionChannels) {
        return new SetDistributionChannels(distributionChannels.stream().map(Referenceable::toResourceIdentifier).collect(Collectors.toList()));
    }
}
