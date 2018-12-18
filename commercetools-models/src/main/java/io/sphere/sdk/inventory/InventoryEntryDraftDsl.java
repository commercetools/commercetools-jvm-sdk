package io.sphere.sdk.inventory;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class InventoryEntryDraftDsl extends InventoryEntryDraftDslBase<InventoryEntryDraftDsl>{

    InventoryEntryDraftDsl(@Nullable CustomFieldsDraft custom, @Nullable ZonedDateTime expectedDelivery, Long quantityOnStock, @Nullable Integer restockableInDays, String sku, @Nullable ResourceIdentifier<Channel> supplyChannel) {
        super(custom, expectedDelivery, quantityOnStock, restockableInDays, sku, supplyChannel);
    }


    public InventoryEntryDraftDsl withSupplyChannel(@Nullable Referenceable<Channel> supplyChannel) {
        return super.withSupplyChannel(Optional.ofNullable(supplyChannel).map(Referenceable::toResourceIdentifier).orElse(null));
    }
}
