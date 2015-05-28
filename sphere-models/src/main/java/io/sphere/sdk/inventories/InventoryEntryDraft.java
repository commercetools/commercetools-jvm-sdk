package io.sphere.sdk.inventories;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.time.ZonedDateTime;
import java.util.Optional;

public class InventoryEntryDraft extends Base {
    private final String sku;
    private final long quantityOnStock;
    private final Optional<Integer> restockableInDays;
    private final Optional<ZonedDateTime> expectedDelivery;
    private final Optional<Reference<Channel>> supplyChannel;

    private InventoryEntryDraft(final String sku, final long quantityOnStock, final Optional<ZonedDateTime> expectedDelivery, final Optional<Integer> restockableInDays, final Optional<Reference<Channel>> supplyChannel) {
        this.expectedDelivery = expectedDelivery;
        this.sku = sku;
        this.quantityOnStock = quantityOnStock;
        this.restockableInDays = restockableInDays;
        this.supplyChannel = supplyChannel;
    }

    public InventoryEntryDraft withExpectedDelivery(final ZonedDateTime expectedDelivery) {
        return of(sku, quantityOnStock, Optional.of(expectedDelivery), restockableInDays, supplyChannel);
    }

    public InventoryEntryDraft withSupplyChannel(final Referenceable<Channel> supplyChannel) {
        return of(sku, quantityOnStock, expectedDelivery, restockableInDays, Optional.of(supplyChannel.toReference()));
    }

    public InventoryEntryDraft withRestockableInDays(final int restockableInDays) {
        return of(sku, quantityOnStock, expectedDelivery, Optional.of(restockableInDays), supplyChannel);
    }

    public static InventoryEntryDraft of(final String sku, final long quantityOnStock) {
        return of(sku, quantityOnStock, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static InventoryEntryDraft of(final String sku, final long quantityOnStock, final Optional<ZonedDateTime> expectedDelivery, final Optional<Integer> restockableInDays, final Optional<Reference<Channel>> supplyChannel) {
        return new InventoryEntryDraft(sku, quantityOnStock, expectedDelivery, restockableInDays, supplyChannel);
    }

    public Optional<ZonedDateTime> getExpectedDelivery() {
        return expectedDelivery;
    }

    public long getQuantityOnStock() {
        return quantityOnStock;
    }

    public Optional<Integer> getRestockableInDays() {
        return restockableInDays;
    }

    public String getSku() {
        return sku;
    }

    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }
}
