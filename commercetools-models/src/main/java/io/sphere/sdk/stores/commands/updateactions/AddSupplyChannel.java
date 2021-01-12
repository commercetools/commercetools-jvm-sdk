package io.sphere.sdk.stores.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;

/**
 * Add a Supply Channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest}
 *
 * @see Store#getSupplyChannels()
 */
public final class AddSupplyChannel extends UpdateActionImpl<Store> {
    private final ResourceIdentifier<Channel> supplyChannel;

    private AddSupplyChannel(final Reference<Channel> supplyChannel) {
        super("addSupplyChannel");
        this.supplyChannel = supplyChannel;
    }

    public ResourceIdentifier<Channel> getSupplyChannel() {
        return supplyChannel;
    }

    public static AddSupplyChannel of(final Referenceable<Channel> supplyChannel) {
        return new AddSupplyChannel(supplyChannel.toReference());
    }
}
