package io.sphere.sdk.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class InventoryEntryDraftDsl extends Base implements InventoryEntryDraft {
    private final String sku;
    private final Long quantityOnStock;
    @Nullable
    private final Integer restockableInDays;
    @Nullable
    private final ZonedDateTime expectedDelivery;
    @Nullable 
    private final Reference<Channel> supplyChannel;

    @JsonCreator
    InventoryEntryDraftDsl(final String sku, final Long quantityOnStock, @Nullable final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, @Nullable final Reference<Channel> supplyChannel) {
        this.expectedDelivery = expectedDelivery;
        this.sku = sku;
        this.quantityOnStock = quantityOnStock;
        this.restockableInDays = restockableInDays;
        this.supplyChannel = supplyChannel;
    }

    public InventoryEntryDraftDsl withExpectedDelivery(@Nullable final ZonedDateTime expectedDelivery) {
        return of(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel);
    }

    public InventoryEntryDraft withSupplyChannel(@Nullable final Referenceable<Channel> supplyChannel) {
        final Reference<Channel> channelReference = Optional.ofNullable(supplyChannel).map(x -> x.toReference()).orElse(null);
        return of(sku, quantityOnStock, expectedDelivery, restockableInDays, channelReference);
    }

    public InventoryEntryDraftDsl withRestockableInDays(@Nullable final Integer restockableInDays) {
        return of(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel);
    }

    public static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock) {
        return of(sku, quantityOnStock, null, null, null);
    }

    public static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock, final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, final Reference<Channel> supplyChannel) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel);
    }

    @Override
    @Nullable
    public ZonedDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    @Override
    @Nullable
    public Long getQuantityOnStock() {
        return quantityOnStock;
    }

    @Override
    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    @Nullable
    public Reference<Channel> getSupplyChannel() {
        return supplyChannel;
    }
}
