package io.sphere.sdk.inventories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.time.Instant;
import java.util.Optional;

@JsonDeserialize(as = InventoryEntryImpl.class)
public interface InventoryEntry extends DefaultModel<InventoryEntry> {
    long getAvailableQuantity();

    Optional<Instant> getExpectedDelivery();

    long getQuantityOnStock();

    Optional<Integer> getRestockableInDays();

    String getSku();

    Optional<Reference<Channel>> getSupplyChannel();

    //reservations are internal

    default Reference<InventoryEntry> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "inventory-entry";
    }

    static TypeReference<InventoryEntry> typeReference(){
        return new TypeReference<InventoryEntry>() {
            @Override
            public String toString() {
                return "TypeReference<InventoryEntry>";
            }
        };
    }
}
