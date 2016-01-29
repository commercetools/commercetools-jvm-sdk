package io.sphere.sdk.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Inventory allows you to track stock quantity per SKU and optionally per supply {@link Channel}.
 *
 * @see io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand
 * @see io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommand
 * @see io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand
 * @see io.sphere.sdk.inventory.queries.InventoryEntryQuery
 * @see io.sphere.sdk.inventory.queries.InventoryEntryByIdGet
 */
@JsonDeserialize(as = InventoryEntryImpl.class)
public interface InventoryEntry extends Resource<InventoryEntry> {
    /**
     * Available amount of stock. (available means: quantityOnStock - reserved quantity)
     * @return quantity
     */
    Long getAvailableQuantity();

    /**
     * The date and time of the next restock.
     * @return date time or null
     */
    @Nullable
    ZonedDateTime getExpectedDelivery();

    /**
     * Overall amount of stock. (available + reserved)
     * @return quantity
     */
    Long getQuantityOnStock();

    /**
     * The time period in days, that tells how often this inventory entry is restocked.
     * @return time in days or null
     */
    @Nullable
    Integer getRestockableInDays();

    String getSku();

    /**
     * Optional connection to particular supplier.
     * @return channel or null
     */
    @Nullable
    Reference<Channel> getSupplyChannel();

    //reservations are internal

    default Reference<InventoryEntry> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
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

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<InventoryEntry> typeReference() {
        return new TypeReference<InventoryEntry>() {
            @Override
            public String toString() {
                return "TypeReference<InventoryEntry>";
            }
        };
    }

    static Reference<InventoryEntry> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
