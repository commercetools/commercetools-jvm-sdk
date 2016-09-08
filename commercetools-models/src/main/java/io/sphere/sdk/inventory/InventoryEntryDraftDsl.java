package io.sphere.sdk.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;

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
    @Nullable
    private final CustomFieldsDraft custom;

    @JsonCreator
    InventoryEntryDraftDsl(final String sku, final Long quantityOnStock, @Nullable final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, @Nullable final Reference<Channel> supplyChannel, @Nullable final CustomFieldsDraft custom) {
        this.expectedDelivery = expectedDelivery;
        this.sku = sku;
        this.quantityOnStock = quantityOnStock;
        this.restockableInDays = restockableInDays;
        this.supplyChannel = supplyChannel;
        this.custom = custom;
    }

    public InventoryEntryDraftDsl withExpectedDelivery(@Nullable final ZonedDateTime expectedDelivery) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel, custom);
    }

    public InventoryEntryDraft withSupplyChannel(@Nullable final Referenceable<Channel> supplyChannel) {
        final Reference<Channel> channelReference = Optional.ofNullable(supplyChannel).map(x -> x.toReference()).orElse(null);
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, channelReference, custom);
    }

    public InventoryEntryDraftDsl withRestockableInDays(@Nullable final Integer restockableInDays) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel, custom);
    }

    public InventoryEntryDraftDsl withCustom(@Nullable final CustomFieldsDraft custom) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel, custom);
    }

    public static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock) {
        return of(sku, quantityOnStock, null, null, null);
    }

    public static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock, final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, final Reference<Channel> supplyChannel) {
        return new InventoryEntryDraftDsl(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel, null);
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

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }
}
