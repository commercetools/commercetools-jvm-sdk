package io.sphere.sdk.inventories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.Optional;

final class InventoryEntryImpl extends DefaultModelImpl<InventoryEntry> implements InventoryEntry {
    private final String sku;
    private final Optional<Reference<Channel>> supplyChannel;
    private final long quantityOnStock;
    private final long availableQuantity;
    private final Optional<Integer> restockableInDays;
    private final Optional<Instant> expectedDelivery;

    @JsonCreator
    public InventoryEntryImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final long availableQuantity, final String sku, final Optional<Reference<Channel>> supplyChannel, final long quantityOnStock, final Optional<Integer> restockableInDays, final Optional<Instant> expectedDelivery) {
        super(id, version, createdAt, lastModifiedAt);
        this.availableQuantity = availableQuantity;
        this.sku = sku;
        this.supplyChannel = supplyChannel;
        this.quantityOnStock = quantityOnStock;
        this.restockableInDays = restockableInDays;
        this.expectedDelivery = expectedDelivery;
    }

    @Override
    public long getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public Optional<Instant> getExpectedDelivery() {
        return expectedDelivery;
    }

    @Override
    public long getQuantityOnStock() {
        return quantityOnStock;
    }

    @Override
    public Optional<Integer> getRestockableInDays() {
        return restockableInDays;
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public Optional<Reference<Channel>> getSupplyChannel() {
        return supplyChannel;
    }
}
