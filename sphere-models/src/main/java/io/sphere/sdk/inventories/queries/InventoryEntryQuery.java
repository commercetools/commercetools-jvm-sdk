package io.sphere.sdk.inventories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;

/**
 {@doc.gen summary inventory entries}
 */
public interface InventoryEntryQuery extends UltraQueryDsl<InventoryEntry, InventoryEntryQuery, InventoryEntryQueryModel<InventoryEntry>, InventoryEntryExpansionModel<InventoryEntry>> {
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
