package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Sets/unsets the supply channel.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandIntegrationTest#setSupplyChannel()}
 */
public final class SetSupplyChannel extends UpdateActionImpl<InventoryEntry> {
    @Nullable
    private final Reference<Channel> supplyChannel;

    private SetSupplyChannel(@Nullable final Reference<Channel> supplyChannel) {
        super("setSupplyChannel");
        this.supplyChannel = supplyChannel;
    }

    public static SetSupplyChannel of(@Nullable final Referenceable<Channel> supplyChannel) {
        final Reference<Channel> channelReference = Optional.ofNullable(supplyChannel)
                .map(Referenceable::toReference)
                .orElse(null);
        return new SetSupplyChannel(channelReference);
    }

    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
