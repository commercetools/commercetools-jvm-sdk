package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.InventoryService;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.internal.command.InventoryCommands.CreateInventoryEntry;
import io.sphere.internal.request.FetchRequestBasedOnQuery;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;

public class InventoryServiceImpl extends ProjectScopedAPI<InventoryEntry> implements InventoryService {
    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<InventoryEntry>() {}, new TypeReference<QueryResult<InventoryEntry>>(){});
    }

    @Override
    public CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock) {
        return createCommandRequest(endpoints.inventory.root(), new CreateInventoryEntry(sku, quantityOnStock));
    }

    @Override
    public FetchRequest<InventoryEntry> bySku(String sku) {
        QueryRequest<InventoryEntry> queryRequest = query().where("sku = \"" + sku + "\" and supplyChannel is not defined");
        return new FetchRequestBasedOnQuery(queryRequest);
    }

    @Override
    public QueryRequest<InventoryEntry> query() {
        return queryImpl(endpoints.inventory.root());
    }
}
