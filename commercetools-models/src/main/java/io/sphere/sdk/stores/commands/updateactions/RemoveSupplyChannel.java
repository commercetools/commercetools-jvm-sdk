package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

/**
 * Remove a Supply Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getSupplyChannels()
 */
public final class RemoveSupplyChannel extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<Channel> supplyChannel;

    private RemoveSupplyChannel(final Reference<Channel> supplyChannel) {
        super("removeSupplyChannel");
        this.supplyChannel = supplyChannel;
    }

    public ResourceIdentifier<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    public static RemoveSupplyChannel of(final Referenceable<Channel> supplyChannel) {
        return new RemoveSupplyChannel(supplyChannel.toReference());
    }
}
