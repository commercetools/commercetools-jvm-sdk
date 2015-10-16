package io.sphere.sdk.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = InventoryEntryImpl.class)
public interface InventoryEntry extends Resource<InventoryEntry> {
    Long getAvailableQuantity();

    @Nullable
    ZonedDateTime getExpectedDelivery();

    Long getQuantityOnStock();

    @Nullable
    Integer getRestockableInDays();

    String getSku();

    @Nullable
    Reference<Channel> getSupplyChannel();

    //reservations are internal

    default Reference<InventoryEntry> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String referenceTypeId(){
        return "inventory-entry";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
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
