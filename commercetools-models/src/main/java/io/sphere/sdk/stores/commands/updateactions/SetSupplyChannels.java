package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Set a Supply Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getSupplyChannels()
 */
public final class SetSupplyChannels extends UpdateActionImpl<Store> {
    private final List<ResourceIdentifier<Channel>> supplyChannels;

    private SetSupplyChannels(final List<ResourceIdentifier<Channel>> supplyChannels) {
        super("setSupplyChannels");
        this.supplyChannels = supplyChannels;
    }

    public List<ResourceIdentifier<Channel>> getSupplyChannels() {
        return supplyChannels;
    }

    public static SetSupplyChannels of(final List<Referenceable<Channel>> supplyChannels) {
        return new SetSupplyChannels(supplyChannels.stream().map(Referenceable::toResourceIdentifier).collect(Collectors.toList()));
    }
}
