package io.sphere.sdk.inventory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Inventory allows you to track stock quantity per SKU and optionally per supply {@link Channel}.
 *
 * <p>An InventoryEntry can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 *
 * @see io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand
 * @see io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommand
 * @see io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand
 * @see io.sphere.sdk.inventory.queries.InventoryEntryQuery
 * @see io.sphere.sdk.inventory.queries.InventoryEntryByIdGet
 */
@JsonDeserialize(as = InventoryEntryImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "inventory entries", pathElement = "inventory")
@HasByIdGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.inventory.commands.InventoryEntryCreateCommandIntegrationTest#execution()")
@HasUpdateCommand
@HasDeleteCommand
@HasQueryModel
public interface InventoryEntry extends Resource<InventoryEntry>, Custom {
    /**
     * Available amount of stock. (available means: quantityOnStock - reserved quantity)
     * @return quantity
     * @see io.sphere.sdk.inventory.commands.updateactions.AddQuantity
     * @see io.sphere.sdk.inventory.commands.updateactions.RemoveQuantity
     * @see io.sphere.sdk.inventory.commands.updateactions.ChangeQuantity
     */
    Long getAvailableQuantity();

    /**
     * The date and time of the next restock.
     * @return date time or null
     * @see io.sphere.sdk.inventory.commands.updateactions.SetExpectedDelivery
     */
    @Nullable
    @IgnoreInQueryModel
    ZonedDateTime getExpectedDelivery();

    /**
     * Overall amount of stock. (available + reserved)
     * @return quantity
     * @see io.sphere.sdk.inventory.commands.updateactions.AddQuantity
     * @see io.sphere.sdk.inventory.commands.updateactions.RemoveQuantity
     * @see io.sphere.sdk.inventory.commands.updateactions.ChangeQuantity
     */
    Long getQuantityOnStock();

    /**
     * The time period in days, that tells how often this inventory entry is restocked.
     * @return time in days or null
     * @see io.sphere.sdk.inventory.commands.updateactions.SetRestockableInDays
     */
    @Nullable
    Integer getRestockableInDays();

    String getSku();

    /**
     * Optional connection to particular supplier.
     * @return channel or null
     * @see io.sphere.sdk.inventory.commands.updateactions.SetSupplyChannel
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

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<InventoryEntry> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "inventory-entry";
    }

    @Nullable
    @Override
    CustomFields getCustom();
}
