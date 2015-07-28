package io.sphere.sdk.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = InventoryEntryImpl.class)
public interface InventoryEntry extends DefaultModel<InventoryEntry> {
    long getAvailableQuantity();

    @Nullable
    ZonedDateTime getExpectedDelivery();

    long getQuantityOnStock();

    @Nullable
    Integer getRestockableInDays();

    String getSku();

    @Nullable
    Reference<Channel> getSupplyChannel();

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
