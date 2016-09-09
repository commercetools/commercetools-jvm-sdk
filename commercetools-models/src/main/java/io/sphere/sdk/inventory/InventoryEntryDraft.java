package io.sphere.sdk.inventory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@JsonDeserialize(as = InventoryEntryDraftDsl.class)
public interface InventoryEntryDraft extends CustomDraft {
    @Nullable
    ZonedDateTime getExpectedDelivery();

    @Nullable
    Long getQuantityOnStock();

    @Nullable
    Integer getRestockableInDays();

    String getSku();

    @Nullable
    Reference<Channel> getSupplyChannel();

    @Nullable
    @Override
    CustomFieldsDraft getCustom();

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock) {
        return of(sku, quantityOnStock, null, null, null);
    }

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock, @Nullable final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, final Referenceable<Channel> supplyChannel) {
        final Reference<Channel> channel = Optional.ofNullable(supplyChannel).map(s -> s.toReference()).orElse(null);
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, channel, null);
    }
}
