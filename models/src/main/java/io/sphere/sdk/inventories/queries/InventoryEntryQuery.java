package io.sphere.sdk.inventories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.inventories.InventoryEntry;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.List;

/**
 {@doc.gen summary inventory entries}
 */
public class InventoryEntryQuery extends DefaultModelQuery<InventoryEntry> {

    private InventoryEntryQuery() {
        super(Endpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<InventoryEntry>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<InventoryEntry>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<InventoryEntry>>";
            }
        };
    }

    public static InventoryEntryQuery of() {
        return new InventoryEntryQuery();
    }

    public static InventoryEntryQueryModel model() {
        return InventoryEntryQueryModel.get();
    }
}
