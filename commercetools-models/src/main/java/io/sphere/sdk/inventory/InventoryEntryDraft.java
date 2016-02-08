package io.sphere.sdk.inventory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = InventoryEntryDraftDsl.class)
public interface InventoryEntryDraft {
    @Nullable
    ZonedDateTime getExpectedDelivery();

    @Nullable
    Long getQuantityOnStock();

    @Nullable
    Integer getRestockableInDays();

    String getSku();

    @Nullable
    Reference<Channel> getSupplyChannel();

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock) {
        return of(sku, quantityOnStock, null, null, null);
    }

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock, final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, final Reference<Channel> supplyChannel) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel);
    }
}
