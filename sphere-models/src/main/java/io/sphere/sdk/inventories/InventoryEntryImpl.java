package io.sphere.sdk.inventories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

final class InventoryEntryImpl extends DefaultModelImpl<InventoryEntry> implements InventoryEntry {
    private final String sku;
    @Nullable
    private final Reference<Channel> supplyChannel;
    private final long quantityOnStock;
    private final long availableQuantity;
    @Nullable
    private final Integer restockableInDays;
    @Nullable
    private final ZonedDateTime expectedDelivery;

    @JsonCreator
    public InventoryEntryImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final long availableQuantity, final String sku, final Reference<Channel> supplyChannel, final long quantityOnStock, final Integer restockableInDays, final ZonedDateTime expectedDelivery) {
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
    @Nullable
    public ZonedDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    @Override
    public long getQuantityOnStock() {
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
