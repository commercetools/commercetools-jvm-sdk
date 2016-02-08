package io.sphere.sdk.inventory.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.expansion.InventoryEntryExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 {@doc.gen summary inventory entries}
 */
public interface InventoryEntryQuery extends MetaModelQueryDsl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel, InventoryEntryExpansionModel<InventoryEntry>> {
    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedQueryResult<InventoryEntry>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<InventoryEntry>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<InventoryEntry>>";
            }
        };
    }

    static InventoryEntryQuery of() {
        return new InventoryEntryQueryImpl();
    }
}
